package com.elinmejorabletv.ui.mobile.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elinmejorabletv.domain.model.Track
import com.elinmejorabletv.domain.usecase.ManageFavoritesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val manageFavoritesUseCase: ManageFavoritesUseCase
) : ViewModel() {

    private val _favoriteTracks = MutableLiveData<FavoriteTracksState>(FavoriteTracksState.Loading)
    val favoriteTracks: LiveData<FavoriteTracksState> = _favoriteTracks

    init {
        loadFavoriteTracks()
    }

    fun loadFavoriteTracks() {
        viewModelScope.launch {
            _favoriteTracks.value = FavoriteTracksState.Loading

            manageFavoritesUseCase.getFavoriteTracks()
                .catch { e ->
                    _favoriteTracks.value = FavoriteTracksState.Error(
                        message = e.message ?: "Error desconocido al cargar favoritos"
                    )
                }
                .collectLatest { tracks ->
                    _favoriteTracks.value = if (tracks.isEmpty()) {
                        FavoriteTracksState.Empty
                    } else {
                        FavoriteTracksState.Success(tracks)
                    }
                }
        }
    }

    fun removeFromFavorites(trackId: String) {
        viewModelScope.launch {
            manageFavoritesUseCase.removeFromFavorites(trackId)
            loadFavoriteTracks()
        }
    }

    sealed class FavoriteTracksState {
        object Loading : FavoriteTracksState()
        data class Success(val tracks: List<Track>) : FavoriteTracksState()
        object Empty : FavoriteTracksState()
        data class Error(val message: String) : FavoriteTracksState()
    }
}