package com.tetyukov.practicum_proj.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tetyukov.practicum_proj.creator.Creator
import com.tetyukov.practicum_proj.domain.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(
    private val tracksRepository: TracksRepository
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    fun search(query: String) {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) {
            _searchScreenState.update { SearchState.Initial }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _searchScreenState.update { SearchState.Searching }
            try {
                // Важно: предполагается, что searchTracks() может бросать IOException
                val list = tracksRepository.searchTracks(expression = trimmed)
                _searchScreenState.update { SearchState.Success(tracks = list) }
            } catch (e: IOException) {
                _searchScreenState.update {
                    SearchState.Fail(error = e.message ?: "Network error")
                }
            } catch (e: Exception) {
                // Подстраховка, чтобы не падать от неожиданных ошибок/парсинга
                _searchScreenState.update {
                    SearchState.Fail(error = e.message ?: "Unexpected error")
                }
            }
        }
    }

    companion object {
        fun getViewModelFactory(context: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(Creator.getTracksRepository(context)) as T
                }
            }
    }
}