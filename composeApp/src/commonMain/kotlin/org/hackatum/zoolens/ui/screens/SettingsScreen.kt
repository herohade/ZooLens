package org.hackatum.zoolens.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.hackatum.zoolens.i18n.LocalStrings

@Composable
fun SettingsScreen(
    language: String,
    onLanguageChange: (String) -> Unit,
    useDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val isGerman = language.startsWith("de")
    val isEnglish = language.startsWith("en")
    val isLight = !useDarkTheme
    val isDark = useDarkTheme
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(LocalStrings.current.settings, style = MaterialTheme.typography.headlineMedium)
        HorizontalDivider(Modifier.padding(vertical = 8.dp))
        Text(LocalStrings.current.settingsGeneral, style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.Start).padding(start = 24.dp))
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(LocalStrings.current.settingsLanguage, style = MaterialTheme.typography.bodyLarge)
            Row {
                RadioButton(
                    selected = isGerman,
                    onClick = { onLanguageChange("de") },
                    colors = RadioButtonDefaults.colors()
                )
                Text(LocalStrings.current.settingsLanguageGerman, modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(Modifier.width(8.dp))
                RadioButton(
                    selected = isEnglish,
                    onClick = { onLanguageChange("en") },
                    colors = RadioButtonDefaults.colors()
                )
                Text(LocalStrings.current.settingsLanguageEnglish, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
        Row(Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(LocalStrings.current.settingsTheme, style = MaterialTheme.typography.bodyLarge)
            Row {
                RadioButton(
                    selected = isLight,
                    onClick = { onThemeChange(false) },
                    colors = RadioButtonDefaults.colors()
                )
                Text(LocalStrings.current.settingsThemeLightLabel, modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(Modifier.width(8.dp))
                RadioButton(
                    selected = isDark,
                    onClick = { onThemeChange(true) },
                    colors = RadioButtonDefaults.colors()
                )
                Text(LocalStrings.current.settingsThemeDarkLabel, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
        HorizontalDivider(Modifier.padding(vertical = 8.dp))
        Text(LocalStrings.current.settingsAbout, style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.Start).padding(start = 24.dp))
        Spacer(Modifier.height(8.dp))
        Text(LocalStrings.current.settingsAboutVersion, style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.Start).padding(start = 24.dp))
        Text(LocalStrings.current.settingsAboutCopyright, style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.Start).padding(start = 24.dp))
    }
}
