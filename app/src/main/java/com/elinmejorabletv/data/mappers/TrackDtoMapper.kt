package com.elinmejorabletv.data.mappers

import com.elinmejorabletv.data.api.models.TrackDto
import com.elinmejorabletv.data.database.entities.TrackEntity
import java.text.SimpleDateFormat
import java.util.Locale

fun TrackDto.toEntity(): TrackEntity {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'", Locale.US)

    return TrackEntity(
        trackId = trackId,
        name = name,
        lastUpdated = dateFormat.parse(lastUpdated) ?: java.util.Date(),
        isActive = isActive,
        rtmpUrl = generatedRtmpUrl,
        streamToken = streamToken,
        securityTokenExpiresAt = dateFormat.parse(securityTokenExpiresAt) ?: java.util.Date(),
        priority = priority,
        isStreaming = isStreaming,
        state = state
    )
}