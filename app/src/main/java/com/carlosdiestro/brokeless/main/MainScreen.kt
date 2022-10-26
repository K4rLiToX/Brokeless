package com.carlosdiestro.brokeless.main

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.carlosdiestro.brokeless.R
import com.carlosdiestro.brokeless.core.navigation.MainNavGraph
import com.carlosdiestro.brokeless.core.navigation.NavigationDirections

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navItems = listOf(
        NavigationDirections.Main.budget to R.drawable.ic_budget,
        NavigationDirections.Main.wallet to R.drawable.ic_wallet,
        NavigationDirections.Main.statistics to R.drawable.ic_statistics
    )

    val mainDestinations = listOf(
        NavigationDirections.Main.budget.destination,
        NavigationDirections.Main.wallet.destination,
        NavigationDirections.Main.statistics.destination
    )

    val showBottomNavigation = rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(key1 = currentDestination) {
        showBottomNavigation.value = navController.currentDestination?.route in mainDestinations
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomNavigation.value,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                NavigationBar {

                    navItems.forEach { screen ->
                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.primary,
                            ),
                            icon = {
                                Icon(
                                    painter = painterResource(id = screen.second),
                                    contentDescription = "Nav Item Icon"
                                )
                            },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.first.destination } == true,
                            onClick = {
                                navController.navigate(screen.first.destination) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        MainNavGraph(
            modifier = Modifier.padding(paddingValues),
            navController = navController
        )
    }
}