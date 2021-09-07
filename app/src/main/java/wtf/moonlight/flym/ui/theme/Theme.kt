package wtf.moonlight.flym.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val darkColorPalette = darkColors(
    primary = teal800,
    primaryVariant = teal800Darker,
    secondary = tealA700,
)

private val lightColorPalette = lightColors(
    primary = teal400,
    primaryVariant = teal400Darker,
    secondary = tealA400
)

@Composable
fun FlymTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorPalette
    } else {
        lightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}