package com.elinmejorabletv.data.api.models

import com.google.gson.annotations.SerializedName

data class TrackDto(
    @SerializedName("TrackId")
    val trackId: String,

    @SerializedName("Name")
    val name: String,

    @SerializedName("LastUpdated")
    val lastUpdated: String,

    @SerializedName("IsActive")
    val isActive: Boolean,

    @SerializedName("GeneratedRtmpUrl")
    val generatedRtmpUrl: String,

    @SerializedName("GeneratedSrtUrl")
    val generatedSrtUrl: String,

    @SerializedName("StreamToken")
    val streamToken: String,

    @SerializedName("SecurityTokenExpiresAt")
    val securityTokenExpiresAt: String,

    @SerializedName("Priority")
    val priority: Int,

    @SerializedName("IsStreaming")
    val isStreaming: Boolean,

    @SerializedName("BytesProcessed")
    val bytesProcessed: Long,

    @SerializedName("LastStatusUpdate")
    val lastStatusUpdate: String,

    @SerializedName("State")
    val state: String
)