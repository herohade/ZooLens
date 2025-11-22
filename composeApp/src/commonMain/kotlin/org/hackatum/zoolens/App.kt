package org.hackatum.zoolens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

private enum class MainTab(val label: String) {
    Home("Home"),
    Search("Search"),
    AI("AI"),
    Map("Map"),
    Settings("Settings")
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        var selectedIndex by remember { mutableIntStateOf(0) }
        val tabs = remember { MainTab.entries }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    tabs.forEachIndexed { index, tab ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            icon = { Text(tab.label.take(1)) },
                            label = { Text(tab.label) }
                        )
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .safeContentPadding(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = tabs[selectedIndex].label, style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}