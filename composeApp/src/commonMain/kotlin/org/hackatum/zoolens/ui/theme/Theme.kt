package org.hackatum.zoolens.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light palette
private val AccentSecondaryLight = Color(0xFFE4E4E4)
private val BackgroundMainLight = Color(0xFFECE6DF)
private val BackgroundSecondaryLight = Color(0xFFE4D7CC)
private val HighlightMainLight = Color(0xFFF2AF00)
private val TextMainLight = Color(0xFF4A2000)

// Dark palette
private val AccentSecondaryDark = Color(0xFFE4E4E4)
private val BackgroundMainDark = Color(0xFF1A1715)
private val BackgroundSecondaryDark = Color(0xFF231F1C)
private val HighlightMainDark = Color(0xFFFFBD2F)
private val TextMainDark = Color(0xFFF2E5D6)

private val LightColors = lightColorScheme(
    primary = HighlightMainLight,
    onPrimary = Color(0xFF000000),
    secondary = AccentSecondaryLight,
    onSecondary = TextMainLight,

    background = BackgroundMainLight,
    onBackground = TextMainLight,

    surface = BackgroundSecondaryLight,
    onSurface = TextMainLight,
)

private val DarkColors = darkColorScheme(
    primary = HighlightMainDark,
    onPrimary = Color(0xFF000000),

    secondary = AccentSecondaryDark,
    onSecondary = TextMainDark,

    background = BackgroundMainDark,
    onBackground = TextMainDark,

    surface = BackgroundSecondaryDark,
    onSurface = TextMainDark,
)

@Composable
fun ZoolensTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
