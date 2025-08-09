package com.elinmejorabletv.ui.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.elinmejorabletv.domain.model.Track
import com.elinmejorabletv.ui.theme.ElInmejorableTVTheme
import com.elinmejorabletv.ui.tv.player.TvPlayerScreen
import java.util.Date

class TvPlayerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rtmpUrl = intent.getStringExtra(EXTRA_RTMP_URL) ?: ""
        val trackName = intent.getStringExtra(EXTRA_TRACK_NAME) ?: ""
        val trackId = intent.getStringExtra(EXTRA_TRACK_ID) ?: ""
        val state = intent.getStringExtra(EXTRA_TRACK_STATE) ?: ""
        val isStreaming = intent.getBooleanExtra(EXTRA_IS_STREAMING, false)
        val priority = intent.getIntExtra(EXTRA_PRIORITY, 0)

        val track = Track(
            trackId = trackId,
            name = trackName,
            lastUpdated = Date(),
            isActive = true,
            state = state,
            rtmpUrl = rtmpUrl,
            streamToken = "",
            isStreaming = isStreaming,
            priority = priority
        )

        setContent {
            ElInmejorableTVTheme {
                TvPlayerScreen(
                    track = track,
                    onBackClick = { finish() }
                )
            }
        }
    }

    companion object {
        const val EXTRA_RTMP_URL = "extra_rtmp_url"
        const val EXTRA_TRACK_NAME = "extra_track_name"
        const val EXTRA_TRACK_ID = "extra_track_id"
        const val EXTRA_TRACK_STATE = "extra_track_state"
        const val EXTRA_IS_STREAMING = "extra_is_streaming"
        const val EXTRA_PRIORITY = "extra_priority"
    }
}