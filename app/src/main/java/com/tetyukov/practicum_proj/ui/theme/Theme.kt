package com.tetyukov.practicum_proj.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    primary = Blue,
    onPrimary = White,
    surface = LightGray,
    onSurface = Black,
    outline = Gray
)

@Composable
fun PlaylistmakerandroidTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}