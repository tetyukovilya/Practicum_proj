package com.tetyukov.practicum_proj

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun PlaylistHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.name
    ) {
        // Main screen
        composable(Screen.MAIN.name) {
            MainScreen(
                onSearchClick = { navigateToSearch(navController) },
                onSettingsClick = { navigateToSettings(navController) }
            )
        }

        // Search
        composable(Screen.SEARCH.name) {
            SearchScreen(
                onBackClick = { navigateToMain(navController) }
            )
        }

        // Settings
        composable(Screen.SETTINGS.name) {
            SettingsScreen(
                onBackClick = { navigateToMain(navController) }
            )
        }
    }
}

// Nav funcs
private fun navigateToMain(navController: NavHostController) {
    navController.popBackStack()
}

private fun navigateToSearch(navController: NavHostController) {
    navController.navigate(Screen.SEARCH.name)
}

private fun navigateToSettings(navController: NavHostController) {
    navController.navigate(Screen.SETTINGS.name)
}