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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.hackatum.zoolens.i18n.LocalStrings
import org.hackatum.zoolens.i18n.stringsFor
import org.hackatum.zoolens.ui.navigation.Route
import org.hackatum.zoolens.ui.screens.AIScreen
import org.hackatum.zoolens.ui.screens.HomeScreen
import org.hackatum.zoolens.ui.screens.MapScreen
import org.hackatum.zoolens.ui.screens.SearchScreen
import org.hackatum.zoolens.ui.screens.SettingsScreen
import org.hackatum.zoolens.ui.theme.ZoolensTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ZoolensTheme {
        var language by remember { mutableStateOf("en") }

        var selectedIndex by remember { mutableStateOf(0) }
        val routes = remember { Route.entries }

        CompositionLocalProvider(LocalStrings provides stringsFor(language)) {
            Scaffold(
            bottomBar = {
                NavigationBar {
                    routes.forEachIndexed { index, route ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            icon = {
                                val txt = when (route) {
                                    Route.Home -> LocalStrings.current.home
                                    Route.Search -> LocalStrings.current.search
                                    Route.AI -> LocalStrings.current.ai
                                    Route.Map -> LocalStrings.current.map
                                    Route.Settings -> LocalStrings.current.settings
                                }
                                Text(txt.take(1))
                            },
                            label = {
                                val txt = when (route) {
                                    Route.Home -> LocalStrings.current.home
                                    Route.Search -> LocalStrings.current.search
                                    Route.AI -> LocalStrings.current.ai
                                    Route.Map -> LocalStrings.current.map
                                    Route.Settings -> LocalStrings.current.settings
                                }
                                Text(txt)
                            }
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
                    when (routes[selectedIndex]) {
                        Route.Home -> HomeScreen()
                        Route.Search -> SearchScreen()
                        Route.AI -> AIScreen()
                        Route.Map -> MapScreen()
                        Route.Settings -> SettingsScreen(language) { lang -> language = lang }
                    }
                }
            }
        }
    }
}