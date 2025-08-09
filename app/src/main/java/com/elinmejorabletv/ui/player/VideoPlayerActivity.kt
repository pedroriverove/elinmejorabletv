package com.elinmejorabletv.ui.player

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.elinmejorabletv.R
import com.elinmejorabletv.databinding.ActivityVideoPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoPlayerBinding
    private var player: ExoPlayer? = null
    private var rtmpUrl: String? = null
    private var trackName: String? = null
    private var isRetrying = false
    private var retryCount = 0
    private val maxRetryCount = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rtmpUrl = intent.getStringExtra(EXTRA_RTMP_URL)
        trackName = intent.getStringExtra(EXTRA_TRACK_NAME)

        binding.tvTitle.text = trackName ?: getString(R.string.live_stream)

        setupPlayer()
    }

    private fun setupPlayer() {
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setConnectTimeoutMs(15000)
            .setReadTimeoutMs(15000)
            .setAllowCrossProtocolRedirects(true)

        val dataSourceFactory = DefaultDataSource.Factory(this, httpDataSourceFactory)

        val mediaSourceFactory = DefaultMediaSourceFactory(dataSourceFactory)
            .setLoadErrorHandlingPolicy(createErrorHandlingPolicy())

        player = ExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()
            .also { exoPlayer ->
                binding.playerView.player = exoPlayer

                rtmpUrl?.let { url ->
                    val mediaItem = MediaItem.fromUri(Uri.parse(url))
                    exoPlayer.setMediaItem(mediaItem)
                }

                exoPlayer.playWhenReady = true
                exoPlayer.prepare()
            }

        player?.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                if (retryCount < maxRetryCount && !isRetrying) {
                    retryCount++
                    isRetrying = true
                    retryPlayback()
                } else {
                    binding.errorView.visibility = View.VISIBLE
                    binding.btnRetry.setOnClickListener {
                        binding.errorView.visibility = View.GONE
                        retryCount = 0
                        isRetrying = false
                        retryPlayback()
                    }
                }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    isRetrying = false
                }
            }
        })
    }

    private fun createErrorHandlingPolicy(): LoadErrorHandlingPolicy {
        return DefaultLoadErrorHandlingPolicy(/* minimumLoadableRetryCount= */ 5)
    }

    private fun retryPlayback() {
        player?.let { exoPlayer ->
            exoPlayer.stop()
            exoPlayer.clearMediaItems()

            rtmpUrl?.let { url ->
                val mediaItem = MediaItem.fromUri(Uri.parse(url))
                exoPlayer.setMediaItem(mediaItem)
            }

            exoPlayer.prepare()
            exoPlayer.play()
        }
    }

    override fun onResume() {
        super.onResume()
        player?.play()
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }

    companion object {
        const val EXTRA_RTMP_URL = "extra_rtmp_url"
        const val EXTRA_TRACK_NAME = "extra_track_name"
    }
}