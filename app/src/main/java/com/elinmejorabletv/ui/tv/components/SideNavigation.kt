package com.elinmejorabletv.ui.tv.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.ListItem
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.elinmejorabletv.ui.theme.Orange

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SideNavigation(
    selectedRegion: String,
    onRegionSelected: (String) -> Unit,
    currentUser: String = "pedroriverove",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(Color(0xFF02333A))
            .padding(16.dp)
    ) {
        // App Logo
        Text(
            text = "ElInmejorableTV",
            style = MaterialTheme.typography.headlineMedium,
            color = Orange,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Text(
            text = "Las mejores carreras en vivo",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Categories
        Text(
            text = "CATEGORÍAS",
            style = MaterialTheme.typography.labelLarge,
            color = Color.LightGray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        RegionItem(
            title = "DESTACADOS",
            isSelected = selectedRegion.isEmpty(),
            onClick = { onRegionSelected("") }
        )

        RegionItem(
            title = "USA",
            isSelected = selectedRegion == "US",
            onClick = { onRegionSelected("US") }
        )

        RegionItem(
            title = "LATINOAMÉRICA",
            isSelected = selectedRegion == "LATAM",
            onClick = { onRegionSelected("LATAM") }
        )

        RegionItem(
            title = "EUROPA",
            isSelected = selectedRegion == "EU",
            onClick = { onRegionSelected("EU") }
        )

        RegionItem(
            title = "ASIA",
            isSelected = selectedRegion == "ASIA",
            onClick = { onRegionSelected("ASIA") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Other menu items
        Text(
            text = "CUENTA",
            style = MaterialTheme.typography.labelLarge,
            color = Color.LightGray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        RegionItem(
            title = "MIS FAVORITOS",
            isSelected = false,
            onClick = { /* Navigate to favorites */ }
        )

        RegionItem(
            title = currentUser.uppercase(),
            isSelected = false,
            onClick = { /* Navigate to account */ }
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun RegionItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    ListItem(
        selected = isSelected,
        onClick = onClick,
        headlineContent = {
            Text(title)
        },
        modifier = Modifier.fillMaxWidth()
    )

    // Destacar visualmente el item seleccionado
    if (isSelected) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Orange)
                .padding(horizontal = 8.dp)
        )
    }
}