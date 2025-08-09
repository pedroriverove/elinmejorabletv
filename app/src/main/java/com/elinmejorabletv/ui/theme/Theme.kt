package com.elinmejorabletv.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.darkColorScheme

@OptIn(ExperimentalTvMaterial3Api::class)
private val DarkColorScheme = darkColorScheme(
    primary = Orange,
    secondary = Yellow,
    tertiary = DarkBlue,
    background = Color(0xFF000000),
    surface = Color(0xFF121212)
)

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ElInmejorableTVTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}