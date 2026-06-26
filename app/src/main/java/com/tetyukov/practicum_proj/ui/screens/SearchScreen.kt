package com.tetyukov.practicum_proj.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tetyukov.practicum_proj.ui.components.AppTopBar
import com.tetyukov.practicum_proj.ui.components.TrackListItem
import com.tetyukov.practicum_proj.ui.viewmodel.SearchState
import com.tetyukov.practicum_proj.ui.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onTrackClick: (Long) -> Unit,
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.factory())
) {
    var searchText by remember { mutableStateOf("") }
    val screenState by viewModel.searchState.collectAsState()

    LaunchedEffect(searchText) {
        viewModel.onQueryChanged(searchText)
    }

    Scaffold(
        topBar = { AppTopBar(title = "Поиск", onBackClick = onBackClick) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Поиск") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Поиск"
                    )
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { searchText = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Очистить"
                            )
                        }
                    }
                }
            )

            when (val state = screenState) {
                SearchState.Idle -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Введите запрос для поиска")
                    }
                }

                SearchState.Loading -> SearchLoading()

                is SearchState.Content -> {
                    if (state.tracks.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Ничего не найдено")
                        }
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(state.tracks, key = { it.id }) { track ->
                                TrackListItem(track = track) {
                                    viewModel.onTrackOpened(searchText)
                                    onTrackClick(track.id)
                                }
                                HorizontalDivider()
                            }
                        }
                    }
                }

                is SearchState.History -> {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("История поиска")
                            if (state.entries.isNotEmpty()) {
                                Button(onClick = { viewModel.clearHistory() }) {
                                    Text("Очистить")
                                }
                            }
                        }

                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(state.entries) { entry ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            searchText = entry
                                            viewModel.useHistoryEntry(entry)
                                        }
                                        .padding(vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Icon(Icons.Default.Search, contentDescription = null)
                                    Text(entry)
                                }
                                HorizontalDivider()
                            }
                        }
                    }
                }

                is SearchState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(state.message)
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}