package com.elinmejorabletv.ui.tv.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.elinmejorabletv.domain.model.Track
import com.elinmejorabletv.ui.theme.Orange
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TrackDetails(
    track: Track,
    modifier: Modifier = Modifier
) {
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF02333A).copy(alpha = 0.9f))
            .padding(16.dp)
    ) {
        Text(
            text = track.name,
            style = androidx.tv.material3.MaterialTheme.typography.headlineMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = track.state,
            style = androidx.tv.material3.MaterialTheme.typography.bodyLarge,
            color = Orange
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (track.isStreaming) {
                LiveIndicator()
                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                text = "Actualizado: ${timeFormat.format(track.lastUpdated)}",
                style = androidx.tv.material3.MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Fecha: ${dateFormat.format(track.lastUpdated)}",
            style = androidx.tv.material3.MaterialTheme.typography.bodyMedium,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ID: ${track.trackId} • Prioridad: ${track.priority}",
            style = androidx.tv.material3.MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun LiveIndicator() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color.Red)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "EN VIVO",
            color = Color.White,
            style = androidx.compose.material3.MaterialTheme.typography.labelSmall
        )
    }
}