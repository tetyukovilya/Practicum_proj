package com.tetyukov.practicum_proj

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tetyukov.practicum_proj.ui.search.SearchScreen
import com.tetyukov.practicum_proj.ui.search.SearchViewModel
import com.tetyukov.practicum_proj.ui.theme.Practicum_projTheme

class MainActivity : ComponentActivity() {

    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModel.getViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practicum_projTheme {
                var selectedScreen by remember { mutableStateOf("home") }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = "Главная"
                                    )
                                },
                                label = { Text("Главная") },
                                selected = selectedScreen == "home",
                                onClick = { selectedScreen = "home" }
                            )

                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Поиск"
                                    )
                                },
                                label = { Text("Поиск") },
                                selected = selectedScreen == "search",
                                onClick = { selectedScreen = "search" }
                            )
                        }
                    }
                ) { innerPadding ->
                    when (selectedScreen) {
                        "home" -> MainScreen(
                            modifier = Modifier.padding(innerPadding),
                            onSearchClick = { selectedScreen = "search" }
                        )
                        "search" -> SearchScreen(
                            modifier = Modifier.padding(innerPadding),
                            viewModel = searchViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit = {}  // ← ДОБАВЬ ЭТО
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "Playlist maker",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            MenuItem(
                text = "Поиск",
                onClick = onSearchClick
            )
        }

        item {
            MenuItem(
                text = "Плейлисты",
                onClick = { /* Обработка нажатия */ }
            )
        }

        item {
            MenuItem(
                text = "Избранное",
                onClick = { /* Обработка нажатия */ }
            )
        }

        item {
            MenuItem(
                text = "Настройки",
                onClick = onSettingsClick  // ← ИСПОЛЬЗУЙ ЗДЕСЬ
            )
        }
    }
}

@Composable
fun MenuItem(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Перейти",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

