package com.tetyukov.practicum_proj.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tetyukov.practicum_proj.creator.Creator
import com.tetyukov.practicum_proj.domain.model.Track
import com.tetyukov.practicum_proj.domain.repository.TracksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavoritesViewModel(
    tracksRepository: TracksRepository
) : ViewModel() {
    val favoriteTracks: StateFlow<List<Track>> = tracksRepository.observeFavoriteTracks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    companion object {
        fun factory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FavoritesViewModel(Creator.getTracksRepository()) as T
            }
        }
    }
}