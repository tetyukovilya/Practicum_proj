package com.tetyukov.practicum_proj.ui.AllTracks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tetyukov.practicum_proj.ui.theme.Practicum_projTheme

class AllTracksActivity : ComponentActivity() {

    // ViewModel instantiation is now cleaner
    private val viewModel by viewModels<AllTracksViewModel> { AllTracksViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practicum_projTheme {
                // Surface provides a background and respects the theme's colors
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Delegate UI to a dedicated screen composable
                    AllTracksScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun AllTracksScreen(viewModel: AllTracksViewModel) {
    // Your UI for displaying tracks goes here.
    // For now, we can just use a placeholder text.
    Text(text = "List of all tracks will be here.")
}