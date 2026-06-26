package com.tetyukov.practicum_proj.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tetyukov.practicum_proj.ui.screens.CreatePlaylistScreen
import com.tetyukov.practicum_proj.ui.screens.FavoritesScreen
import com.tetyukov.practicum_proj.ui.screens.MainScreen
import com.tetyukov.practicum_proj.ui.screens.PlaylistsScreen
import com.tetyukov.practicum_proj.ui.screens.SearchScreen
import com.tetyukov.practicum_proj.ui.screens.SettingsScreen
import com.tetyukov.practicum_proj.ui.screens.TrackDetailsScreen

@Composable
fun PlaylistHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                onSongsClick = { navController.navigate(Screen.Songs.route) },
                onPlaylistsClick = { navController.navigate(Screen.Playlists.route) },
                onFavoritesClick = { navController.navigate(Screen.Favorites.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.Songs.route) {
            SearchScreen(
                onBackClick = { navController.popBackStack() },
                onTrackClick = { trackId -> navController.navigate(Screen.TrackDetails.createRoute(trackId)) }
            )
        }

        composable(Screen.Playlists.route) {
            PlaylistsScreen(
                onBackClick = { navController.popBackStack() },
                onCreatePlaylistClick = { navController.navigate(Screen.CreatePlaylist.route) }
            )
        }

        composable(Screen.CreatePlaylist.route) {
            CreatePlaylistScreen(onBackClick = { navController.popBackStack() })
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onBackClick = { navController.popBackStack() },
                onTrackClick = { trackId -> navController.navigate(Screen.TrackDetails.createRoute(trackId)) }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(onBackClick = { navController.popBackStack() })
        }

        composable(Screen.TrackDetails.route) {
            TrackDetailsScreen(onBackClick = { navController.popBackStack() })
        }
    }
}