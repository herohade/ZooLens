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
import org.hackatum.zoolens.ui.theme.ZoolensTheme
import org.hackatum.zoolens.ui.navigation.Route
import org.hackatum.zoolens.ui.screens.AIScreen
import org.hackatum.zoolens.ui.screens.HomeScreen
import org.hackatum.zoolens.ui.screens.MapScreen
import org.hackatum.zoolens.ui.screens.SearchScreen
import org.hackatum.zoolens.ui.screens.SettingsScreen

@Composable
@Preview
fun App() {
    ZoolensTheme {
        var selectedIndex by remember { mutableIntStateOf(0) }
        val routes = remember { Route.entries }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    routes.forEachIndexed { index, route ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            icon = { Text(route.label.take(1)) },
                            label = { Text(route.label) }
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
                    Route.Settings -> SettingsScreen()
                }
            }
        }
    }
}