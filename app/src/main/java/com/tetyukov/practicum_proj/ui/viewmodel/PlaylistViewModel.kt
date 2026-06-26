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

class PlaylistViewModel(
    playlistsRepository: PlaylistsRepository,
    playlistId: Long
) : ViewModel() {
    val playlist: StateFlow<Playlist?> = playlistsRepository.getPlaylist(playlistId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    companion object {
        fun factory(playlistId: Long): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PlaylistViewModel(
                    playlistsRepository = Creator.getPlaylistsRepository(),
                    playlistId = playlistId
                ) as T
            }
        }
    }
}