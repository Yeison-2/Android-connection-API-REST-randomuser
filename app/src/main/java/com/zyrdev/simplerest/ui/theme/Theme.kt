package com.zyrdev.simplerest.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Esquema de colores para el tema oscuro
 * Siguiendo las guías de Material Design 3
 */
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDarkTheme,
    onPrimary = Color.Black,
    primaryContainer = PrimaryDark,
    onPrimaryContainer = PrimaryLight,

    secondary = SecondaryDarkTheme,
    onSecondary = Color.Black,
    secondaryContainer = SecondaryDark,
    onSecondaryContainer = SecondaryLight,

    tertiary = AccentDarkTheme,
    onTertiary = Color.Black,
    tertiaryContainer = AccentDark,
    onTertiaryContainer = AccentLight,

    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,

    surfaceVariant = Color(0xFF424242),
    onSurfaceVariant = TextSecondaryDark,

    error = Error,
    onError = Color.White,

    outline = Color(0xFF616161),
    outlineVariant = Color(0xFF424242)
)

/**
 * Esquema de colores para el tema claro
 * Siguiendo las guías de Material Design 3
 */
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = PrimaryDark,

    secondary = Secondary,
    onSecondary = Color.White,
    secondaryContainer = SecondaryLight,
    onSecondaryContainer = SecondaryDark,

    tertiary = Accent,
    onTertiary = Color.White,
    tertiaryContainer = AccentLight,
    onTertiaryContainer = AccentDark,

    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceLight,
    onSurface = TextPrimary,

    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = TextSecondary,

    error = Error,
    onError = Color.White,

    outline = Divider,
    outlineVariant = Color(0xFFEEEEEE)
)

/**
 * Tema principal de la aplicación SimpleRest
 *
 * @param darkTheme Si es verdadero, usa el tema oscuro
 * @param dynamicColor Si es verdadero, usa colores dinámicos (Android 12+)
 * @param content El contenido de la aplicación
 */
@Composable
fun SimpleRestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

