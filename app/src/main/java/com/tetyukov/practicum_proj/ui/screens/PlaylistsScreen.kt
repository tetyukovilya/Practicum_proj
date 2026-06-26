package com.tetyukov.practicum_proj.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tetyukov.practicum_proj.ui.components.AppTopBar
import com.tetyukov.practicum_proj.ui.components.PlaylistListItem
import com.tetyukov.practicum_proj.ui.viewmodel.PlaylistsViewModel

@Composable
fun PlaylistsScreen(
    onBackClick: () -> Unit,
    onCreatePlaylistClick: () -> Unit,
    viewModel: PlaylistsViewModel = viewModel(factory = PlaylistsViewModel.factory())
) {
    val playlists by viewModel.playlists.collectAsState()

    Scaffold(
        topBar = { AppTopBar(title = "Плейлисты", onBackClick = onBackClick) },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreatePlaylistClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить плейлист")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            if (playlists.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Пока нет плейлистов")
                }
            } else {
                LazyColumn {
                    items(playlists, key = { it.id }) { playlist ->
                        PlaylistListItem(playlist = playlist)
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

