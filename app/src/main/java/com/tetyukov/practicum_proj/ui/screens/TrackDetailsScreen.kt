package com.tetyukov.practicum_proj.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PlaylistAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tetyukov.practicum_proj.ui.components.AppTopBar
import com.tetyukov.practicum_proj.ui.components.PlaylistListItem
import com.tetyukov.practicum_proj.ui.viewmodel.TrackDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailsScreen(
    onBackClick: () -> Unit,
    viewModel: TrackDetailsViewModel = viewModel(factory = TrackDetailsViewModel.factory())
) {
    val track by viewModel.track.collectAsState()
    val playlists by viewModel.playlists.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { AppTopBar(title = "Трек", onBackClick = onBackClick) }
    ) { innerPadding ->
        track?.let { currentTrack ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(currentTrack.trackName, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(currentTrack.artistName, color = MaterialTheme.colorScheme.outline)
                    Text("Длительность: ${currentTrack.trackTime}")
                }

                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    Row(
                        modifier = Modifier.clickable { viewModel.toggleFavorite() },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = if (currentTrack.isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Избранное",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(if (currentTrack.isFavorite) "В избранном" else "Добавить в избранное")
                    }

                    Row(
                        modifier = Modifier.clickable { showBottomSheet = true },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PlaylistAdd,
                            contentDescription = "В плейлист",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text("Добавить в плейлист")
                    }
                }

                if (currentTrack.playlistIds.isNotEmpty()) {
                    Text("Трек уже добавлен в ${currentTrack.playlistIds.size} плейлист(ов)")
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Трек не найден")
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(onDismissRequest = { showBottomSheet = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text("Выберите плейлист", style = MaterialTheme.typography.titleMedium)
                if (playlists.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Нет доступных плейлистов")
                    }
                } else {
                    playlists.forEach { playlist ->
                        PlaylistListItem(playlist = playlist) {
                            viewModel.addToPlaylist(playlist.id)
                            showBottomSheet = false
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}