package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = ArtisticPrimaryContainer,
    secondary = ArtisticGlow,
    tertiary = ArtisticPrimary,
    background = ArtisticTextPrimary,
    surface = Color(0xFF2B2221),
    onPrimary = ArtisticOnPrimaryContainer,
    onBackground = ArtisticBackground,
    onSurface = ArtisticBackground
  )

private val LightColorScheme =
  lightColorScheme(
    primary = ArtisticPrimary,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = ArtisticPrimaryContainer,
    onPrimaryContainer = ArtisticOnPrimaryContainer,
    secondary = ArtisticTextSecondary,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    tertiary = ArtisticPrimary,
    background = ArtisticBackground,
    onBackground = ArtisticTextPrimary,
    surface = ArtisticSurface,
    onSurface = ArtisticTextPrimary,
    surfaceVariant = ArtisticPrimaryContainer,
    onSurfaceVariant = ArtisticTextSecondary,
    outline = ArtisticOutline,
    outlineVariant = ArtisticBorder
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
