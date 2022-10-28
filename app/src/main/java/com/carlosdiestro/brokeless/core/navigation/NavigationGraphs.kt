package com.carlosdiestro.brokeless.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.carlosdiestro.brokeless.main.MainScreen
import com.carlosdiestro.brokeless.main.budget.ui.BudgetScreen
import com.carlosdiestro.brokeless.main.new_monthly_transaction.ui.NewMonthlyTransactionScreen
import com.carlosdiestro.brokeless.main.new_transaction.ui.NewTransactionScreen
import com.carlosdiestro.brokeless.main.transactions.ui.TransactionsScreen
import com.carlosdiestro.brokeless.main.wallet.ui.WalletScreen
import com.carlosdiestro.brokeless.main.wallet.ui.monthly_transactions.MonthlyTransactionsScreen
import com.carlosdiestro.brokeless.onboarding.OnBoardingViewModel
import com.carlosdiestro.brokeless.onboarding.ui.balance.OnBoardingBalanceScreen
import com.carlosdiestro.brokeless.onboarding.ui.currency.OnBoardingCurrencyScreen
import com.carlosdiestro.brokeless.onboarding.ui.monthly_transactions.OnBoardingMonthlyTransactionsScreen
import com.carlosdiestro.brokeless.onboarding.ui.new_transaction.OnBoardingNewTransactionScreen
import com.carlosdiestro.brokeless.onboarding.ui.savings.OnBoardingSavingsScreen
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
            val mainNavHostController = rememberNavController()
            MainScreen(mainNavHostController)
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
            BudgetScreen(navController)
        }
        composable(
            route = "${NavigationDirections.Main.newTransactions.destination}/{isPayment}",
            arguments = NavigationDirections.Main.newTransactions.arguments
        ) {
            NewTransactionScreen(
                navController,
                isPayment = it.arguments?.getBoolean("isPayment")!!
            )
        }

        composable(
            route = NavigationDirections.Main.transactions.destination
        ) {
            TransactionsScreen(navController)
        }

        composable(
            route = NavigationDirections.Main.wallet.destination
        ) {
            WalletScreen(navController)
        }
        composable(
            route = "${NavigationDirections.Main.monthlyTransactions.destination}/{page}",
            arguments = NavigationDirections.Main.monthlyTransactions.arguments
        ) {
            MonthlyTransactionsScreen(
                navController
            )
        }
        composable(
            route = "${NavigationDirections.Main.newMonthlyTransaction.destination}/{isExpense}",
            arguments = NavigationDirections.Main.newMonthlyTransaction.arguments
        ) {
            NewMonthlyTransactionScreen(
                navController,
                isExpense = it.arguments?.getBoolean("isExpense")!!
            )
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

fun NavGraphBuilder.onBoardingNavGraph(
    navController: NavHostController
) {
    navigation(
        route = NavigationDirections.OnBoarding.root.destination,
        startDestination = NavigationDirections.OnBoarding.currency.destination
    ) {
        composable(
            route = NavigationDirections.OnBoarding.currency.destination
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(NavigationDirections.OnBoarding.root.destination)
            }
            val parentViewModel = hiltViewModel<OnBoardingViewModel>(parentEntry)
            OnBoardingCurrencyScreen(
                navController,
                parentViewModel
            )
        }

        composable(
            route = NavigationDirections.OnBoarding.balance.destination
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(NavigationDirections.OnBoarding.root.destination)
            }
            val parentViewModel = hiltViewModel<OnBoardingViewModel>(parentEntry)
            OnBoardingBalanceScreen(
                navController,
                parentViewModel
            )
        }

        composable(
            route = "${NavigationDirections.OnBoarding.monthlyTransactions.destination}/{isIncome}",
            arguments = NavigationDirections.OnBoarding.monthlyTransactions.arguments
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(NavigationDirections.OnBoarding.root.destination)
            }
            val parentViewModel = hiltViewModel<OnBoardingViewModel>(parentEntry)
            OnBoardingMonthlyTransactionsScreen(
                navController,
                parentViewModel,
                it.arguments?.getBoolean("isIncome")!!
            )
        }

        composable(
            route = NavigationDirections.OnBoarding.savings.destination
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(NavigationDirections.OnBoarding.root.destination)
            }
            val parentViewModel = hiltViewModel<OnBoardingViewModel>(parentEntry)
            OnBoardingSavingsScreen(
                navController,
                parentViewModel
            )
        }

        composable(
            route = "${NavigationDirections.OnBoarding.newTransaction.destination}/{isExpense}",
            arguments = NavigationDirections.OnBoarding.newTransaction.arguments
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(NavigationDirections.OnBoarding.root.destination)
            }
            val parentViewModel = hiltViewModel<OnBoardingViewModel>(parentEntry)
            OnBoardingNewTransactionScreen(
                navController,
                parentViewModel,
                it.arguments?.getBoolean("isExpense")!!
            )
        }
    }
}
