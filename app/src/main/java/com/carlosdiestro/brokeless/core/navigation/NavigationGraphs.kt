package com.carlosdiestro.brokeless.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.carlosdiestro.brokeless.main.MainScreen
import com.carlosdiestro.brokeless.onboarding.ui.currency.OnBoardingCurrencyScreen
import com.carlosdiestro.brokeless.welcome.ui.WelcomeScreen

@Composable
fun RootNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        route = NavigationDirections.root.destination,
        startDestination = NavigationDirections.welcome.destination
    ) {
        composable(
            route = NavigationDirections.welcome.destination
        ) {
            WelcomeScreen(navHostController)
        }
        onBoardingNavGraph(navHostController)
        composable(
            route = NavigationDirections.Main.root.destination
        ) {
            MainScreen(navHostController)
        }
    }
}

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = NavigationDirections.Main.root.destination,
        startDestination = NavigationDirections.Main.budget.destination
    ) {
        composable(
            route = NavigationDirections.Main.budget.destination
        ) {

        }
        composable(
            route = NavigationDirections.Main.transactions.destination
        ) {

        }

        composable(
            route = NavigationDirections.Main.wallet.destination
        ) {

        }
        composable(
            route = NavigationDirections.Main.incomes.destination
        ) {

        }
        composable(
            route = NavigationDirections.Main.expenses.destination
        ) {

        }
        composable(
            route = NavigationDirections.Main.categoryLimit.destination
        ) {

        }

        composable(
            route = NavigationDirections.Main.statistics.destination
        ) {

        }
        composable(
            route = NavigationDirections.Main.categoryStatistics.destination
        ) {

        }
    }
}

fun NavGraphBuilder.onBoardingNavGraph(navController: NavHostController) {
    navigation(
        route = NavigationDirections.OnBoarding.root.destination,
        startDestination = NavigationDirections.OnBoarding.currency.destination
    ) {
        composable(
            route = NavigationDirections.OnBoarding.currency.destination
        ) {
            OnBoardingCurrencyScreen(
                navController,
                hiltViewModel()
            )
        }

        composable(
            route = NavigationDirections.OnBoarding.balance.destination
        ) {

        }

        composable(
            route = NavigationDirections.OnBoarding.incomes.destination
        ) {

        }

        composable(
            route = NavigationDirections.OnBoarding.expenses.destination
        ) {

        }

        composable(
            route = NavigationDirections.OnBoarding.savings.destination
        ) {

        }
    }
}

