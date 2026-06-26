package com.tetyukov.practicum_proj.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tetyukov.practicum_proj.creator.Creator
import com.tetyukov.practicum_proj.domain.model.Track
import com.tetyukov.practicum_proj.domain.repository.SearchHistoryRepository
import com.tetyukov.practicum_proj.domain.repository.TracksRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface SearchState {
    data object Idle : SearchState
    data object Loading : SearchState
    data class Content(val tracks: List<Track>) : SearchState
    data class History(val entries: List<String>) : SearchState
    data class Error(val message: String) : SearchState
}

class SearchViewModel(
    private val tracksRepository: TracksRepository,
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModel() {
    private val _searchState = MutableStateFlow<SearchState>(SearchState.Idle)
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    private var searchJob: Job? = null

    init {
        showHistory()
    }

    fun onQueryChanged(query: String) {
        searchJob?.cancel()

        if (query.isBlank()) {
            showHistory()
            return
        }

        searchJob = viewModelScope.launch {
            _searchState.value = SearchState.Loading
            delay(300)
            runCatching {
                tracksRepository.searchTracks(query)
            }.onSuccess { tracks ->
                _searchState.value = SearchState.Content(tracks)
            }.onFailure { throwable ->
                _searchState.value = SearchState.Error(
                    throwable.message ?: "Не удалось выполнить поиск"
                )
            }
        }
    }

    fun onTrackOpened(query: String) {
        searchHistoryRepository.addEntry(query)
        viewModelScope.launch {
            if (query.isBlank()) {
                showHistory()
            }
        }
    }

    fun useHistoryEntry(entry: String) {
        onQueryChanged(entry)
    }

    fun clearHistory() {
        viewModelScope.launch {
            searchHistoryRepository.clear()
            _searchState.value = SearchState.History(emptyList())
        }
    }

    private fun showHistory() {
        viewModelScope.launch {
            val entries = searchHistoryRepository.getEntries()
            _searchState.value = if (entries.isEmpty()) {
                SearchState.Idle
            } else {
                SearchState.History(entries)
            }
        }
    }

    companion object {
        fun factory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SearchViewModel(
                    tracksRepository = Creator.getTracksRepository(),
                    searchHistoryRepository = Creator.getSearchHistoryRepository()
                ) as T
            }
        }
    }
}
