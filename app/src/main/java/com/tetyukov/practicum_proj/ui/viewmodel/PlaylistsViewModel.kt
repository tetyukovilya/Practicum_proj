package com.tetyukov.practicum_proj.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tetyukov.practicum_proj.creator.Creator
import com.tetyukov.practicum_proj.domain.model.Playlist
import com.tetyukov.practicum_proj.domain.repository.PlaylistsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlaylistsViewModel(
    private val playlistsRepository: PlaylistsRepository
) : ViewModel() {
    val playlists: StateFlow<List<Playlist>> = playlistsRepository.observePlaylists()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createPlaylist(name: String, description: String, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            playlistsRepository.createPlaylist(name, description)
            onDone()
        }
    }

    companion object {
        fun factory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PlaylistsViewModel(Creator.getPlaylistsRepository()) as T
            }
        }
    }
}