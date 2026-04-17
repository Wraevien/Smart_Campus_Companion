package com.example.smartcampuscompanion.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1565C0),
    secondary = Color(0xFF2E7D32),

    background = Color(0xFFF5F7FB),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFE9EEF5),

    onPrimary = Color.White,
    onSecondary = Color.White,

    onBackground = Color(0xFF1A1F26),
    onSurface = Color(0xFF1A1F26),
    onSurfaceVariant = Color(0xFF5D6876),

    outline = Color(0xFFB8C2CF)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF64B5F6),
    secondary = Color(0xFF66BB6A),

    background = Color(0xFF0E1621),
    surface = Color(0xFF1B2633),
    surfaceVariant = Color(0xFF243241),

    onPrimary = Color.Black,
    onSecondary = Color.Black,

    onBackground = Color(0xFFF3F6FA),
    onSurface = Color(0xFFF3F6FA),
    onSurfaceVariant = Color(0xFFC4CEDA),

    outline = Color(0xFF607080)
)

@Composable
fun SmartCampusCompanionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}