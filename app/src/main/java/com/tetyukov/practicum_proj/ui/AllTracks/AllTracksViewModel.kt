package com.tetyukov.practicum_proj.ui.AllTracks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
// Import this to get the application context in the factory
import androidx.lifecycle.viewmodel.CreationExtras
import com.tetyukov.practicum_proj.creator.Creator
import com.tetyukov.practicum_proj.domain.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class AllTracksViewModel(
    application: Application,
    private val tracksRepository: TracksRepository
) : AndroidViewModel(application) {

    private val _allTracksScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val allTracksScreenState = _allTracksScreenState.asStateFlow()

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allTracksScreenState.value = SearchState.Searching
                val list = tracksRepository.searchTracks("")
                _allTracksScreenState.value = SearchState.Success(foundList = list)
            } catch (e: IOException) {
                _allTracksScreenState.value = SearchState.Fail(e.message ?: "Unknown error")
            }
        }
    }

    companion object {
        // This is the corrected factory
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {

                val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!

                if (modelClass.isAssignableFrom(AllTracksViewModel::class.java)) {
                    return AllTracksViewModel(
                        application = application,
                        tracksRepository = Creator.getTracksRepository(context = application)
                    ) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
