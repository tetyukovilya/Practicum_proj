package com.tetyukov.practicum_proj.ui.AllTracks.composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tetyukov.practicum_proj.R
import com.tetyukov.practicum_proj.data.dto.TrackDto

@Composable
fun TrackListItem(track: TrackDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // You can use a placeholder image for now
        Image(
            painter = painterResource(id = R.drawable.music),
            contentDescription = "Track Artwork for ${track.trackName}",
            modifier = Modifier.size(45.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = track.trackName,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = track.artistName
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = track.trackTimeMillis)
    }
}
