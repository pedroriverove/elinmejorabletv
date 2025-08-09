package com.elinmejorabletv.utils

import com.elinmejorabletv.domain.model.Track

object TrackParcelableUtils {
    // Esta función ya no es necesaria ya que no usamos Parcelable
    fun trackToBundle(track: Track): Map<String, String> {
        return mapOf(
            "trackId" to track.trackId,
            "name" to track.name,
            "rtmpUrl" to track.rtmpUrl,
            "streamToken" to track.streamToken
        )
    }
}