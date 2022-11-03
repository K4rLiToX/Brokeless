package com.carlosdiestro.brokeless.main

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.carlosdiestro.brokeless.R
import com.carlosdiestro.brokeless.core.navigation.MainNavGraph
import com.carlosdiestro.brokeless.core.navigation.NavigationDirections
import com.carlosdiestro.brokeless.core.ui.theme.Black
import com.carlosdiestro.brokeless.core.ui.theme.White

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
    val density = LocalDensity.current
    LaunchedEffect(key1 = currentDestination) {
        showBottomNavigation.value = navController.currentDestination?.route in mainDestinations
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomNavigation.value,
                enter = slideInVertically {
                    with(density) { (it/2).dp.roundToPx() }
                } + fadeIn(),
                exit = slideOutVertically {
                    with(density) { (it/2).dp.roundToPx() }
                } + fadeOut()
            ) {
                NavigationBar(
                    modifier = Modifier.shadow(24.dp, ambientColor = Black, spotColor = Black),
                    containerColor = White,
                    tonalElevation = 0.dp
                ) {

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