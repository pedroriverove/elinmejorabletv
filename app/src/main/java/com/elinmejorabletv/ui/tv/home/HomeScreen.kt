package com.elinmejorabletv.ui.tv.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import org.koin.androidx.compose.koinViewModel
import com.elinmejorabletv.domain.model.Track
import com.elinmejorabletv.ui.mobile.home.HomeViewModel
import com.elinmejorabletv.ui.theme.DarkBlue
import com.elinmejorabletv.ui.theme.Orange
import com.elinmejorabletv.ui.tv.components.ChannelCard
import com.elinmejorabletv.ui.tv.components.SideNavigation

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onTrackSelected: (Track) -> Unit = {},
    currentDate: String = "2025-08-09",
    currentUser: String = "pedroriverove"
) {
    val tracksState by viewModel.tracksState.observeAsState()
    val selectedRegion by viewModel.selectedRegion.observeAsState("US")

    Row(modifier = Modifier.fillMaxSize()) {
        // Side Navigation
        SideNavigation(
            selectedRegion = selectedRegion,
            onRegionSelected = { region -> viewModel.setRegion(region) },
            currentUser = currentUser,
            modifier = Modifier
                .width(240.dp)
                .fillMaxSize()
                .background(DarkBlue)
        )

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ElInmejorable TV",
                    style = androidx.tv.material3.MaterialTheme.typography.headlineLarge,
                    color = Orange
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = currentDate,
                    style = androidx.tv.material3.MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Hipódromos disponibles para $selectedRegion",
                style = androidx.tv.material3.MaterialTheme.typography.bodyLarge,
                color = Color.LightGray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            when (val state = tracksState) {
                is HomeViewModel.TracksState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Cargando hipódromos...",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                is HomeViewModel.TracksState.Success -> {
                    ChannelGrid(tracks = state.tracks, onTrackClick = onTrackSelected)
                }
                is HomeViewModel.TracksState.Empty -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "No hay hipódromos disponibles para esta región",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                is HomeViewModel.TracksState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Error: ${state.message}",
                                color = Color.Red,
                                textAlign = TextAlign.Center
                            )

                            androidx.tv.material3.Button(
                                onClick = { viewModel.refreshTracks() },
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text(text = "Reintentar")
                            }
                        }
                    }
                }
                null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Cargando...",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ChannelGrid(tracks: List<Track>, onTrackClick: (Track) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 240.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(tracks) { track ->
            ChannelCard(
                track = track,
                onClick = { onTrackClick(track) }
            )
        }
    }
}