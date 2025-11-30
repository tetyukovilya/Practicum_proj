package com.tetyukov.practicum_proj

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tetyukov.practicum_proj.ui.theme.Practicum_projTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practicum_projTheme {
                val navController = rememberNavController()
                PlaylistHost(navController = navController)
            }
        }
    }
}

@Composable
fun MainScreen(
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
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
                onClick = {
                    onSearchClick()
                }
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
                onClick = {
                    onSettingsClick()
                }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Practicum_projTheme {
        MainScreen(
            onSearchClick = {},
            onSettingsClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkPreview() {
    Practicum_projTheme(darkTheme = true) {
        MainScreen(
            onSearchClick = {},
            onSettingsClick = {}
        )
    }
}