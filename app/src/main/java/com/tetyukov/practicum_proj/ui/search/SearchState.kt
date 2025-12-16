package com.tetyukov.practicum_proj.ui.search

import com.tetyukov.practicum_proj.domain.TrackDto

sealed class SearchState {
    data object Initial : SearchState()                 // Экран открыт, поиска ещё не было
    data object Searching : SearchState()               // Идёт запрос
    data class Success(val tracks: List<TrackDto>) : SearchState() // Успешно получили список
    data class Fail(val error: String) : SearchState()  // Ошибка запроса/сети
}