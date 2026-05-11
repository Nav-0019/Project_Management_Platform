package com.example.myapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    background = DarkBackground,
    surface = DarkCardBackground,
    onPrimary = DarkTextColor,
    onBackground = DarkTextColor,
    onSurface = DarkTextColor,
    onSurfaceVariant = DarkMutedTextColor,
    outline = DarkBorderColor,
    error = Danger
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    background = Background,
    surface = CardBackground,
    onPrimary = Color.White,
    onBackground = TextColor,
    onSurface = TextColor,
    onSurfaceVariant = MutedTextColor,
    outline = BorderColor,
    error = Danger
)

@Composable
fun ProManageTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
