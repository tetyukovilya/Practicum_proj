package com.tetyukov.practicum_proj.ui.AllTracks

import android.app.Application // <-- Import Application
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
// Import the key to get the application context
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tetyukov.practicum_proj.creator.Creator
import com.tetyukov.practicum_proj.domain.TracksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import java.io.IOException

class AllTracksViewModel(private val tracksRepository: TracksRepository) : ViewModel() {

    private val _allTracksScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val allTracksScreenState = _allTracksScreenState.asStateFlow()

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allTracksScreenState.value = SearchState.Loading
                val list = tracksRepository.searchTracks("") // для всех треков можно передать empty string
                _allTracksScreenState.value = SearchState.Success(foundList = list)
            } catch (e: IOException) {
                _allTracksScreenState.value = SearchState.Error(e.message ?: "Unknown")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Get the application context from the CreationExtras (this)
                val application = this[APPLICATION_KEY] as Application

                // Now you can pass the context to your Creator
                AllTracksViewModel(
                    tracksRepository = Creator.getTracksRepository(context = application)
                )
            }
        }
    }
}