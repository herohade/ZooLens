package org.hackatum.zoolens.i18n

import androidx.compose.runtime.staticCompositionLocalOf
data class AppStrings(
    val home: String,
    val search: String,
    val ai: String,
    val map: String,
    val settings: String,
    val toggleLanguage: String,
    val noResult: String,
    val askAssistant: String,
)

val EnStrings = AppStrings(
    home = "Home",
    search = "Search",
    ai = "AI",
    map = "Map",
    settings = "Settings",
    toggleLanguage = "Toggle Language",
    noResult = "No results",
    askAssistant = "Ask Mogli",
)

val DeStrings = AppStrings(
    home = "Start",
    search = "Suche",
    ai = "KI",
    map = "Karte",
    settings = "Einstellungen",
    toggleLanguage = "Sprache umschalten",
    noResult = "Keine Treffer",
    askAssistant = "Frag Mogli",
)

val LocalStrings = staticCompositionLocalOf { EnStrings }

fun stringsFor(language: String?): AppStrings = when (language) {
    "de", "de-DE", "de_AT", "de-AT", "de-CH", "de-CH" -> DeStrings
    else -> EnStrings
}
