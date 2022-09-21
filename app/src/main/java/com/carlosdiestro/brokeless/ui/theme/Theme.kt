package com.carlosdiestro.brokeless.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Blue50,
    secondary = Blue70,
    background = Blue90,
    surface = Blue70,
    onPrimary = Shadow10,
    onSecondary = Shadow10,
    onBackground = Shadow10,
    onSurface = Shadow10
)

private val LightColorScheme = lightColorScheme(
    primary = Blue50,
    secondary = Blue70,
    background = Blue90,
    surface = Blue70,
    onPrimary = Shadow10,
    onSecondary = Shadow10,
    onBackground = Shadow10,
    onSurface = Shadow10
)

@Composable
fun BrokelessTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}