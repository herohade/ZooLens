package org.hackatum.zoolens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavDestination
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

@Composable
fun AndroidApp() {
    ZoolensTheme {
        var language by remember { mutableStateOf("en") }

        val routes = remember { Route.entries }
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination: NavDestination? = backStackEntry?.destination

        // Keep Search tab highlighted when on the non-tab Animal route
        val selectedIndex = run {
            val dest = currentDestination?.route
            when {
                dest == "Animal" -> routes.indexOf(Route.Search)
                else -> routes.indexOfFirst { it.name == dest }.takeIf { it >= 0 } ?: 0
            }
        }

        CompositionLocalProvider(LocalStrings provides stringsFor(language)) {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        routes.forEachIndexed { index, route ->
                            val labelText = when (route) {
                                Route.Home -> LocalStrings.current.home
                                Route.Search -> LocalStrings.current.search
                                Route.AI -> LocalStrings.current.ai
                                Route.Map -> LocalStrings.current.map
                                Route.Settings -> LocalStrings.current.settings
                            }
                            NavigationBarItem(
                                selected = selectedIndex == index,
                                onClick = {
                                    if (currentDestination?.route != route.name) {
                                        navController.navigate(route.name) {
                                            launchSingleTop = true
                                            restoreState = true
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                        }
                                    }
                                },
                                icon = { Text(labelText.take(1)) },
                                label = { Text(labelText) }
                            )
                        }
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .safeContentPadding()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.Home.name
                    ) {
                        composable(Route.Home.name) { HomeScreen() }
                        composable(Route.Search.name) {
                            SearchScreen(onOpenAnimal = { id ->
                                navController.currentBackStackEntry?.savedStateHandle?.set("animalId", id)
                                navController.navigate("Animal")
                            })
                        }
                        composable(Route.AI.name) { AIScreen() }
                        composable(Route.Map.name) { MapScreen() }
                        composable(Route.Settings.name) {
                            SettingsScreen(
                                language = language,
                                onLanguageChange = { lang -> language = lang }
                            )
                        }
                        // Non-tab destination: Animal detail
                        composable("Animal") { backStackEntry ->
//                            val id = backStackEntry.arguments?.getString("id") ?: ""
                            val id = backStackEntry.savedStateHandle.get<String>("animalId") ?: ""
                            AnimalScreen(id = id)
                        }
                    }
                }
            }
        }
    }
}
