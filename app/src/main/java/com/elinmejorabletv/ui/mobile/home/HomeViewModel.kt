package com.elinmejorabletv.ui.mobile.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elinmejorabletv.domain.model.Track
import com.elinmejorabletv.domain.usecase.GetActiveTracksUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(private val getActiveTracksUseCase: GetActiveTracksUseCase) : ViewModel() {

    private val _tracksState = MutableLiveData<TracksState>(TracksState.Loading)
    val tracksState: LiveData<TracksState> = _tracksState

    private val _selectedRegion = MutableLiveData("US")
    val selectedRegion: LiveData<String> = _selectedRegion

    private var isRefreshing = false

    init {
        loadTracks("US")  // Default to US region
        refreshTracks()
    }

    fun setRegion(region: String) {
        _selectedRegion.value = region
        loadTracks(region)
    }

    private fun loadTracks(region: String) {
        if (isRefreshing) return

        viewModelScope.launch {
            _tracksState.value = TracksState.Loading

            getActiveTracksUseCase(region)
                .catch { e ->
                    _tracksState.value = TracksState.Error(e.message ?: "Unknown error")
                }
                .collectLatest { tracks ->
                    _tracksState.value = if (tracks.isEmpty()) {
                        TracksState.Empty
                    } else {
                        TracksState.Success(tracks)
                    }
                }
        }
    }

    fun refreshTracks() {
        viewModelScope.launch {
            isRefreshing = true
            try {
                getActiveTracksUseCase.refreshTracks()
                // The flow will automatically emit the new data
            } catch (e: IOException) {
                // If refresh fails, but we have cached data, we'll still show it
                // We could notify the user about the refresh failure if needed
            } finally {
                isRefreshing = false
            }
        }
    }

    sealed class TracksState {
        object Loading : TracksState()
        data class Success(val tracks: List<Track>) : TracksState()
        object Empty : TracksState()
        data class Error(val message: String) : TracksState()
    }
}