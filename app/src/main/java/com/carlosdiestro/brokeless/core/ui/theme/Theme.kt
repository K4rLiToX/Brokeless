package com.carlosdiestro.brokeless.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Blue20,
    onPrimary = Black,
    secondary = Blue10,
    onSecondary = Black,
    background = Blue10,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    onSurfaceVariant = Grey,
    error = Red40,
)

private val LightColorScheme = lightColorScheme(
    primary = Blue20,
    onPrimary = Black,
    secondary = Blue10,
    onSecondary = Black,
    background = Blue10,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    onSurfaceVariant = Grey,
    error = Red40,
)

@Composable
fun BrokelessTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = BrokelessTypography,
        shapes = BrokelessShape,
        content = content
    )
}