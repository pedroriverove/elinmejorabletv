package com.elinmejorabletv.ui.tv

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.elinmejorabletv.domain.model.Track
import com.elinmejorabletv.ui.theme.ElInmejorableTVTheme
import com.elinmejorabletv.ui.tv.home.HomeScreen

class TvMainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentDate = "2025-08-09"
        val currentUser = "pedroriverove"

        setContent {
            ElInmejorableTVTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TvApp(
                        onTrackSelected = { track ->
                            navigateToPlayer(track)
                        },
                        currentDate = currentDate,
                        currentUser = currentUser
                    )
                }
            }
        }
    }

    private fun navigateToPlayer(track: Track) {
        val intent = Intent(this, TvPlayerActivity::class.java).apply {
            putExtra(TvPlayerActivity.EXTRA_RTMP_URL, track.rtmpUrl)
            putExtra(TvPlayerActivity.EXTRA_TRACK_NAME, track.name)
            putExtra(TvPlayerActivity.EXTRA_TRACK_ID, track.trackId)
            putExtra(TvPlayerActivity.EXTRA_TRACK_STATE, track.state)
            putExtra(TvPlayerActivity.EXTRA_IS_STREAMING, track.isStreaming)
            putExtra(TvPlayerActivity.EXTRA_PRIORITY, track.priority)
        }
        startActivity(intent)
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TvApp(
    onTrackSelected: (Track) -> Unit = {},
    currentDate: String = "2025-08-09",
    currentUser: String = "pedroriverove"
) {
    HomeScreen(
        onTrackSelected = onTrackSelected,
        currentDate = currentDate,
        currentUser = currentUser
    )
}