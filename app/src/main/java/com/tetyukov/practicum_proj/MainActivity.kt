package com.tetyukov.practicum_proj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.tetyukov.practicum_proj.creator.Creator
import com.tetyukov.practicum_proj.navigation.PlaylistHost
import com.tetyukov.practicum_proj.ui.theme.PlaylistmakerandroidTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Creator.init(applicationContext)

        setContent {
            PlaylistmakerandroidTheme {
                val navController = rememberNavController()
                PlaylistHost(navController = navController)
            }
        }
    }
}