package com.tetyukov.practicum_proj.navigation

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object Songs : Screen("songs")
    data object Playlists : Screen("playlists")
    data object Favorites : Screen("favorites")
    data object Settings : Screen("settings")
    data object CreatePlaylist : Screen("create_playlist")
    data object TrackDetails : Screen("track_details/{trackId}") {
        fun createRoute(trackId: Long): String = "track_details/$trackId"
    }
}