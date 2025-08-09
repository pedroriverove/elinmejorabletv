package com.elinmejorabletv.data.mappers

import com.elinmejorabletv.data.database.entities.TrackEntity
import com.elinmejorabletv.domain.model.Track

fun TrackEntity.toModel(): Track {
    return Track(
        trackId = trackId,
        name = name,
        lastUpdated = lastUpdated,
        isActive = isActive,
        state = state,
        rtmpUrl = rtmpUrl,
        streamToken = streamToken,
        isStreaming = isStreaming,
        priority = priority
    )
}