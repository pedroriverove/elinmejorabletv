package com.elinmejorabletv.domain.usecase

import com.elinmejorabletv.data.preferences.UserPreferences
import com.elinmejorabletv.data.repository.TrackRepository
import com.elinmejorabletv.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ManageFavoritesUseCase(
    private val trackRepository: TrackRepository,
    private val userPreferences: UserPreferences
) {

    fun getFavoriteTracks(): Flow<List<Track>> {
        return trackRepository.getActiveTracks().map { tracks ->
            val favoriteIds = userPreferences.favoriteTracks
            tracks.filter { track -> favoriteIds.contains(track.trackId) }
                .sortedBy { it.name }
        }
    }

    fun addToFavorites(trackId: String) {
        userPreferences.addFavoriteTrack(trackId)
    }

    fun removeFromFavorites(trackId: String) {
        userPreferences.removeFavoriteTrack(trackId)
    }

    fun isFavorite(trackId: String): Boolean {
        return userPreferences.isFavoriteTrack(trackId)
    }

    fun toggleFavorite(trackId: String): Boolean {
        val isFavorite = isFavorite(trackId)
        if (isFavorite) {
            removeFromFavorites(trackId)
        } else {
            addToFavorites(trackId)
        }
        return !isFavorite
    }
}