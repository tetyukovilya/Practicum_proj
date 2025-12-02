package com.tetyukov.practicum_proj.ui.AllTracks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
// Make sure to import your screen composable
import com.tetyukov.practicum_proj.ui.AllTracks.composable.AllTracksScreen
import com.tetyukov.practicum_proj.ui.theme.Practicum_projTheme

class AllTracksActivity : ComponentActivity() {

    // The activity creates the ViewModel
    private val viewModel by viewModels<AllTracksViewModel> { AllTracksViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent is where you define your UI
        setContent {
            Practicum_projTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // HERE is where you use AllTracksScreen
                    // You pass the ViewModel instance from the activity to the screen.
                    AllTracksScreen(viewModel = viewModel)
                }
            }
        }
    }
}
