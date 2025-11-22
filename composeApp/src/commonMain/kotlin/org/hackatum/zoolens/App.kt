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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.hackatum.zoolens.i18n.LocalStrings
import org.hackatum.zoolens.i18n.stringsFor
import org.hackatum.zoolens.ui.navigation.Route
import org.hackatum.zoolens.ui.screens.AIScreen
import org.hackatum.zoolens.ui.screens.AnimalScreen
import org.hackatum.zoolens.ui.screens.HomeScreen
import org.hackatum.zoolens.ui.screens.MapScreen
import org.hackatum.zoolens.ui.screens.SearchScreen
import org.hackatum.zoolens.ui.screens.SettingsScreen
import org.hackatum.zoolens.ui.theme.ZoolensTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    onNavHostReady: suspend (NavHostController) -> Unit = {}
) {
    ZoolensTheme {
        var language by remember { mutableStateOf("en") }

        val routes = remember { Route.entries }
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val selectedIndex = routes.indexOfFirst { it.name == currentRoute }.let { if (it == -1) 0 else it }

        CompositionLocalProvider(LocalStrings provides stringsFor(language)) {
            Scaffold(
            bottomBar = {
                NavigationBar {
                    routes.forEachIndexed { index, route ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                if (selectedIndex != index) {
                                    navController.navigate(route.name) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
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
                NavHost(
                    navController = navController,
                    startDestination = Route.Home.name
                ) {
                    composable(Route.Home.name) { HomeScreen(navController) }
                    composable(Route.Search.name) {
                        SearchNavHost()
                    }
                    composable(Route.AI.name) { AIScreen() }
                    composable(Route.Map.name) { MapScreen() }
                    composable(Route.Settings.name) { SettingsScreen(language) { lang -> language = lang } }
                }

                LaunchedEffect(navController) {
                    onNavHostReady(navController)
                }
            }
        }
    }
}

@Composable
fun SearchNavHost() {
    val searchNavController = rememberNavController()
    NavHost(
        navController = searchNavController,
        startDestination = "SearchMain"
    ) {
        composable("SearchMain") {
            SearchScreen(onOpenAnimal = { searchNavController.navigate("Animal") })
        }
        composable("Animal") {
            AnimalScreen()
        }
    }
}
