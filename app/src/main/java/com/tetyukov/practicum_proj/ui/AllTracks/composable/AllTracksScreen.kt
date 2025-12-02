package com.tetyukov.practicum_proj.ui.AllTracks.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tetyukov.practicum_proj.ui.AllTracks.AllTracksViewModel
import com.tetyukov.practicum_proj.ui.AllTracks.SearchState

@Composable
fun AllTracksScreen(viewModel: AllTracksViewModel) {
    val screenState by viewModel.allTracksScreenState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val currentState = screenState) {
            is SearchState.Loading -> {
                CircularProgressIndicator()
            }
            is SearchState.Success -> {
                if (currentState.foundList.isEmpty()) {
                    Text(text = "No tracks found")
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        items(currentState.foundList) { track ->
                            // Your TrackListItem composable will display each track
                            TrackListItem(track = track)
                        }
                    }
                }
            }
            is SearchState.Error -> {
                // Show an error message
                Text(text = "Error: ${currentState.errorMessage}")
            }
            is SearchState.Initial -> {
                // Initially, the screen is empty while it waits for the loading state
            }
        }
    }
}
