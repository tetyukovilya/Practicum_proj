package com.tetyukov.practicum_proj.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tetyukov.practicum_proj.ui.components.AppTopBar
import com.tetyukov.practicum_proj.ui.components.TrackListItem
import com.tetyukov.practicum_proj.ui.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(
    onBackClick: () -> Unit,
    onTrackClick: (Long) -> Unit,
    viewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.factory())
) {
    val favoriteTracks by viewModel.favoriteTracks.collectAsState()

    Scaffold(
        topBar = { AppTopBar(title = "Избранное", onBackClick = onBackClick) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            if (favoriteTracks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("В избранном пока ничего нет")
                }
            } else {
                LazyColumn {
                    items(favoriteTracks, key = { it.id }) { track ->
                        TrackListItem(track = track) { onTrackClick(track.id) }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}