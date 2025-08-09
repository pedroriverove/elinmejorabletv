package com.elinmejorabletv.ui.mobile.schedule

import java.util.Date

data class ScheduledTrack(
    val id: String,
    val trackName: String,
    val location: String,
    val imageUrl: String,
    val startTime: Date,
    val endTime: Date,
    val racesCount: Int,
    val isAvailable: Boolean
)