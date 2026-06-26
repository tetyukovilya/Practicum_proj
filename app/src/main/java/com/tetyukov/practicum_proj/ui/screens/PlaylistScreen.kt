package com.tetyukov.practicum_proj.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QueueMusic
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tetyukov.practicum_proj.ui.components.AppTopBar
import com.tetyukov.practicum_proj.ui.components.TrackListItem
import com.tetyukov.practicum_proj.ui.viewmodel.PlaylistViewModel

@Composable
fun PlaylistScreen(
    onBackClick: () -> Unit,
    onTrackClick: (Long) -> Unit,
    playlistId: Long,
    viewModel: PlaylistViewModel = viewModel(factory = PlaylistViewModel.factory(playlistId))
) {
    val playlist by viewModel.playlist.collectAsState()

    Scaffold(
        topBar = { AppTopBar(title = playlist?.name ?: "Плейлист", onBackClick = onBackClick) }
    ) { innerPadding ->
        playlist?.let { currentPlaylist ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.QueueMusic,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(currentPlaylist.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    if (currentPlaylist.description.isNotBlank()) {
                        Text(currentPlaylist.description, color = MaterialTheme.colorScheme.outline)
                    }
                    Text(
                        text = "${currentPlaylist.tracks.size} трек(ов)",
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                if (currentPlaylist.tracks.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("В этом плейлисте пока нет треков")
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(currentPlaylist.tracks, key = { it.id }) { track ->
                            TrackListItem(track = track) { onTrackClick(track.id) }
                            HorizontalDivider()
                        }
                    }
                }
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("Плейлист не найден")
        }
    }
}