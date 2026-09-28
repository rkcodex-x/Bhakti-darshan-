package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DevotionalLightColorScheme = lightColorScheme(
    primary = SaffronPrimary,
    onPrimary = PureWhite,
    primaryContainer = SaffronLight.copy(alpha = 0.2f),
    onPrimaryContainer = SaffronDark,
    secondary = GoldDark,
    onSecondary = PureWhite,
    secondaryContainer = GoldLight.copy(alpha = 0.35f),
    onSecondaryContainer = TempleBrown,
    tertiary = VermilionKumkum,
    onTertiary = PureWhite,
    tertiaryContainer = VermilionKumkum.copy(alpha = 0.15f),
    onTertiaryContainer = VermilionKumkum,
    background = CreamBackground,
    onBackground = TempleTextDark,
    surface = CreamSurface,
    onSurface = TempleTextDark,
    surfaceVariant = CreamSurfaceVariant,
    onSurfaceVariant = TempleTextSecondary,
    outline = CreamCardBorder,
    outlineVariant = GoldLight
)

private val DevotionalDarkColorScheme = darkColorScheme(
    primary = SaffronLight,
    onPrimary = TempleBrown,
    primaryContainer = SaffronDark,
    onPrimaryContainer = GoldLight,
    secondary = GoldAccent,
    onSecondary = TempleBrown,
    secondaryContainer = TempleBrownLight,
    onSecondaryContainer = GoldLight,
    tertiary = GoldLight,
    onTertiary = TempleBrown,
    background = Color(0xFF1A120B),
    onBackground = CreamBackground,
    surface = Color(0xFF261912),
    onSurface = CreamBackground,
    surfaceVariant = Color(0xFF352219),
    onSurfaceVariant = GoldLight.copy(alpha = 0.8f),
    outline = TempleBrownLight,
    outlineVariant = SaffronDark
)

@Composable
fun BhaktiDarshanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DevotionalDarkColorScheme else DevotionalLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
