import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Important: import `items` for lists
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tetyukov.practicum_proj.R
import com.tetyukov.practicum_proj.domain.Track
import com.tetyukov.practicum_proj.ui.AllTracks.AllTracksViewModel
import com.tetyukov.practicum_proj.ui.AllTracks.SearchState

@Composable
fun AllTracksScreen(viewModel: AllTracksViewModel) {
    // Collect the state from the ViewModel
    val screenState by viewModel.allTracksScreenState.collectAsState()

    // Trigger the data fetch when the composable is first launched
    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    // Use a `when` expression to display different UI for each state
    when (val currentState = screenState) {
        is SearchState.Loading -> {
            // Show a loading spinner in the center of the screen
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is SearchState.Success -> {
            // Use LazyColumn to display the list of tracks
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Loop through the list and create a TrackListItem for each track
                items(currentState.foundList) { track ->
                    TrackListItem(track = track)
                }
            }
        }
        is SearchState.Error -> {
            // Show an error message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${currentState.errorMessage}")
            }
        }
        is SearchState.Initial -> {
            // You can show an empty state or nothing initially
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun TrackListItem(track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // Add some vertical padding
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.music),
            contentDescription = "Track cover", // Add content description for accessibility
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(track.trackName, fontWeight = FontWeight.Bold)
            Text(track.artistName)
        }
        Text(track.trackTime)
    }
}