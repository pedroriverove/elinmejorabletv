package com.elinmejorabletv.data.api

import com.elinmejorabletv.data.api.models.TrackDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/api/endpoints/active")
    suspend fun getActiveEndpoints(): Response<List<TrackDto>>
}