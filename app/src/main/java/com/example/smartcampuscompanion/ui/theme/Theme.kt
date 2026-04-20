package com.example.smartcampuscompanion.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ── Colour schemes ────────────────────────────────────────────────────

private val LightColors = lightColorScheme(
    primary              = BluePrimary,
    onPrimary            = Color.White,
    primaryContainer     = BlueLight,
    onPrimaryContainer   = Color(0xFF001D35),
    secondary            = GreenPrimary,
    onSecondary          = Color.White,
    secondaryContainer   = GreenLight,
    onSecondaryContainer = Color(0xFF0A2900),
    error                = ErrorRed,
    onError              = Color.White,
    errorContainer       = ErrorRedLight,
    onErrorContainer     = Color(0xFF410002),
    background           = LightBackground,
    onBackground         = LightOnBg,
    surface              = LightSurface,
    onSurface            = LightOnSurface,
    surfaceVariant       = LightSurfaceVar,
    onSurfaceVariant     = LightOnSurfaceVar,
    outline              = LightOutline,
    outlineVariant       = Divider,
)

private val DarkColors = darkColorScheme(
    primary              = BlueSecondary,
    onPrimary            = Color(0xFF003060),
    primaryContainer     = BlueDark,
    onPrimaryContainer   = Color(0xFFD1E4FF),
    secondary            = Color(0xFF85C455),
    onSecondary          = Color(0xFF1A3410),
    secondaryContainer   = GreenDark,
    onSecondaryContainer = Color(0xFFB8F088),
    error                = Color(0xFFFF6B6B),
    onError              = Color(0xFF690005),
    errorContainer       = Color(0xFF93000A),
    onErrorContainer     = Color(0xFFFFDAD6),
    background           = DarkBackground,
    onBackground         = DarkOnBg,
    surface              = DarkSurface,
    onSurface            = DarkOnSurface,
    surfaceVariant       = DarkSurfaceVar,
    onSurfaceVariant     = DarkOnSurfaceVar,
    outline              = DarkOutline,
    outlineVariant       = Color(0xFF3D4F62),
)

// ── Dark-mode state holder ────────────────────────────────────────────
// Expose this so any screen can read/toggle dark mode without
// threading a param through every composable.

val LocalDarkTheme = staticCompositionLocalOf { mutableStateOf(false) }

@Composable
fun SmartCampusCompanionTheme(
    // Seed from SessionManager so preference survives restarts
    initialDark: Boolean = false,
    content: @Composable () -> Unit,
) {
    val darkState = remember { mutableStateOf(initialDark) }
    val isDark    = darkState.value

    val colorScheme = if (isDark) DarkColors else LightColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        val bgArgb = colorScheme.background.toArgb()
        androidx.compose.runtime.SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = bgArgb
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !isDark
        }
    }

    CompositionLocalProvider(LocalDarkTheme provides darkState) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = Typography,
            shapes      = Shapes,
            content     = content,
        )
    }
}