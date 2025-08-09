package com.elinmejorabletv.domain.usecase

import com.elinmejorabletv.data.repository.TrackRepository
import com.elinmejorabletv.domain.model.Track
import kotlinx.coroutines.flow.Flow

class GetActiveTracksUseCase(private val trackRepository: TrackRepository) {

    operator fun invoke(region: String? = null): Flow<List<Track>> {
        return if (region.isNullOrEmpty()) {
            trackRepository.getActiveTracks()
        } else {
            trackRepository.getActiveTracksByRegion(region)
        }
    }

    suspend fun refreshTracks() {
        trackRepository.refreshTracks()
    }
}