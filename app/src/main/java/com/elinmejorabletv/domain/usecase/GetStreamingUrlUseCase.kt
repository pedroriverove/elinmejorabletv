package com.elinmejorabletv.domain.usecase

import com.elinmejorabletv.data.repository.TrackRepository
import com.elinmejorabletv.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException

class GetStreamingUrlUseCase(private val trackRepository: TrackRepository) {

    operator fun invoke(trackId: String): Flow<Result<String>> {
        return trackRepository.getActiveTracks()
            .map { tracks ->
                val track = tracks.find { it.trackId == trackId }

                if (track != null) {
                    if (track.isStreaming && track.rtmpUrl.isNotEmpty()) {
                        Result.success(track.rtmpUrl)
                    } else {
                        Result.failure(IOException("El hipódromo $trackId no está transmitiendo actualmente"))
                    }
                } else {
                    Result.failure(IOException("No se encontró el hipódromo $trackId"))
                }
            }
    }

    fun getTrackDetails(trackId: String): Flow<Result<Track>> {
        return trackRepository.getActiveTracks()
            .map { tracks ->
                val track = tracks.find { it.trackId == trackId }

                if (track != null) {
                    Result.success(track)
                } else {
                    Result.failure(IOException("No se encontró el hipódromo $trackId"))
                }
            }
    }
}