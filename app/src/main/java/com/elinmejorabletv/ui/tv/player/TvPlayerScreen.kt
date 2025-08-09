package com.elinmejorabletv.ui.tv.player

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.elinmejorabletv.domain.model.Track
import com.elinmejorabletv.ui.theme.DarkBlue
import com.elinmejorabletv.ui.tv.components.TrackDetails
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TvPlayerScreen(
    track: Track,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    var isLoading by remember { mutableStateOf(true) }
    var playbackError by remember { mutableStateOf<String?>(null) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(track.rtmpUrl))
            setMediaItem(mediaItem)
            playWhenReady = true
            prepare()
        }
    }

    LaunchedEffect(track.rtmpUrl) {
        exoPlayer.apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(track.rtmpUrl))
            setMediaItem(mediaItem)
            playWhenReady = true
            prepare()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)

                    player?.addListener(object : Player.Listener {
                        override fun onPlaybackStateChanged(state: Int) {
                            isLoading = state == Player.STATE_BUFFERING
                        }

                        override fun onPlayerError(error: PlaybackException) {
                            playbackError = error.message
                        }
                    })
                }
            }
        )

        when {
            playbackError != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkBlue.copy(alpha = 0.9f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error al cargar la transmisión",
                            color = Color.White,
                            style = androidx.tv.material3.MaterialTheme.typography.headlineSmall
                        )

                        Text(
                            text = playbackError ?: "Error desconocido",
                            color = Color.LightGray,
                            style = androidx.tv.material3.MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )

                        Button(
                            onClick = {
                                playbackError = null
                                exoPlayer.apply {
                                    stop()
                                    val mediaItem = MediaItem.fromUri(Uri.parse(track.rtmpUrl))
                                    setMediaItem(mediaItem)
                                    prepare()
                                    play()
                                }
                            }
                        ) {
                            Text(text = "Reintentar")
                        }

                        Button(
                            onClick = onBackClick,
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(text = "Volver")
                        }
                    }
                }
            }
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }

        // Track info overlay at the bottom
        if (playbackError == null) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                TrackDetails(
                    track = track,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}