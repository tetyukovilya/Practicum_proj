package com.tetyukov.practicum_proj.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tetyukov.practicum_proj.creator.Creator
import com.tetyukov.practicum_proj.domain.model.Playlist
import com.tetyukov.practicum_proj.domain.model.Track
import com.tetyukov.practicum_proj.domain.repository.PlaylistsRepository
import com.tetyukov.practicum_proj.domain.repository.TracksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TrackDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val tracksRepository: TracksRepository,
    playlistsRepository: PlaylistsRepository
) : ViewModel() {
    private val trackId: Long = checkNotNull(savedStateHandle.get<String>("trackId")).toLong()

    val track: StateFlow<Track?> = tracksRepository.observeTrack(trackId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val playlists: StateFlow<List<Playlist>> = combine(
        playlistsRepository.observePlaylists(),
        track.filterNotNull()
    ) { playlists, currentTrack ->
        playlists.filter { it.id !in currentTrack.playlistIds }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleFavorite() {
        val current = track.value ?: return
        viewModelScope.launch {
            tracksRepository.setFavorite(current.id, !current.isFavorite)
        }
    }

    fun addToPlaylist(playlistId: Long) {
        viewModelScope.launch {
            tracksRepository.addTrackToPlaylist(trackId, playlistId)
        }
    }

    companion object {
        fun factory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: androidx.lifecycle.viewmodel.CreationExtras): T {
                @Suppress("UNCHECKED_CAST")
                return TrackDetailsViewModel(
                    savedStateHandle = extras.createSavedStateHandle(),
                    tracksRepository = Creator.getTracksRepository(),
                    playlistsRepository = Creator.getPlaylistsRepository()
                ) as T
            }
        }
    }
}
