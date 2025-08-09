package com.elinmejorabletv.data.repository

import com.elinmejorabletv.data.api.ApiService
import com.elinmejorabletv.data.database.dao.TrackDao
import com.elinmejorabletv.data.mappers.toEntity
import com.elinmejorabletv.data.mappers.toModel
import com.elinmejorabletv.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.Date

class TrackRepository(
    private val apiService: ApiService,
    private val trackDao: TrackDao
) {
    fun getActiveTracksByRegion(region: String): Flow<List<Track>> {
        return trackDao.getActiveTracksByRegion(region).map { entities ->
            entities.map { it.toModel() }
        }
    }

    fun getActiveTracks(): Flow<List<Track>> {
        return trackDao.getActiveTracks().map { entities ->
            entities.map { it.toModel() }
        }
    }

    suspend fun refreshTracks() {
        try {
            val response = apiService.getActiveEndpoints()
            if (response.isSuccessful) {
                response.body()?.let { trackDtos ->
                    val currentTime = Date()
                    val validDtos = trackDtos.filter {
                        try {
                            // Basic validation to filter out malformed data
                            it.trackId.isNotEmpty() &&
                                    it.name.isNotEmpty() &&
                                    it.generatedRtmpUrl.isNotEmpty()
                        } catch (e: Exception) {
                            false
                        }
                    }

                    val entities = validDtos.map { it.toEntity() }
                    trackDao.deleteAll()
                    trackDao.insertAll(entities)
                } ?: throw IOException("Response body is null")
            } else {
                throw IOException("Error fetching tracks: ${response.code()}")
            }
        } catch (e: Exception) {
            throw IOException("Network error: ${e.message}", e)
        }
    }
}