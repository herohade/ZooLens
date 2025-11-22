package org.hackatum.zoolens.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
            Button(onClick = { onLanguageChange(if (language == "en") "de" else "en") }) {
                Text(LocalStrings.current.toggleLanguage)
            }
        }
        Row(Modifier.fillMaxWidth().padding(horizontal = 24.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(LocalStrings.current.settingsTheme, style = MaterialTheme.typography.bodyLarge)
            Button(onClick = { onThemeChange(!useDarkTheme) }) {
                Text(if (useDarkTheme) LocalStrings.current.settingsThemeLight else LocalStrings.current.settingsThemeDark)
            }
        }
        HorizontalDivider(Modifier.padding(vertical = 8.dp))
        Text(LocalStrings.current.settingsAbout, style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.Start).padding(start = 24.dp))
        Spacer(Modifier.height(8.dp))
        Text(LocalStrings.current.settingsAboutVersion, style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.Start).padding(start = 24.dp))
        Text(LocalStrings.current.settingsAboutCopyright, style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.Start).padding(start = 24.dp))
    }
}
