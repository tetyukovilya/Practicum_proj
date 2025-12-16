package com.tetyukov.practicum_proj.ui.AllTracks

import com.tetyukov.practicum_proj.domain.TrackDto

sealed class SearchState {
    val errorMessage: String
        get() {
            TODO()
        }

    object Initial : SearchState()
    object Loading : SearchState()
    data class Success(val foundList: List<TrackDto>) : SearchState()
    data class Error(val error: String) : SearchState()
}