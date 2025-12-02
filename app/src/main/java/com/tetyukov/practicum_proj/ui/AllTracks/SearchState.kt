package com.tetyukov.practicum_proj.ui.AllTracks

import com.tetyukov.practicum_proj.domain.Track

sealed class SearchState {
    val errorMessage: String
        get() {
            TODO()
        }

    object Initial : SearchState()
    object Loading : SearchState()
    data class Success(val foundList: List<Track>) : SearchState()
    data class Error(val error: String) : SearchState()
}