package com.elinmejorabletv.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey
    val trackId: String,
    val name: String,
    val lastUpdated: Date,
    val isActive: Boolean,
    val rtmpUrl: String,
    val streamToken: String,
    val securityTokenExpiresAt: Date,
    val priority: Int,
    val isStreaming: Boolean,
    val state: String
)