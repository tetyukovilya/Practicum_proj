package com.tetyukov.practicum_proj.ui.AllTracks

import com.tetyukov.practicum_proj.domain.TrackDto

sealed class SearchState {
    object Initial : SearchState()
    object Searching : SearchState()
    data class Success(val foundList: List<TrackDto>) : SearchState()
    data class Fail(val error: String) : SearchState()
}