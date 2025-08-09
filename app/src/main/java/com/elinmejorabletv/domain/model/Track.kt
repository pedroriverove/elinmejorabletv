package com.elinmejorabletv.domain.model

import java.util.Date

data class Track(
    val trackId: String,
    val name: String,
    val lastUpdated: Date,
    val isActive: Boolean,
    val state: String,
    val rtmpUrl: String,
    val streamToken: String,
    val isStreaming: Boolean,
    val priority: Int
)