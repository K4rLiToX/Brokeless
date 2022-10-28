package com.carlosdiestro.brokeless.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

object NavigationDirections {
    val root = object : NavigationCommand {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
        override val destination: String
            get() = Destination.Root
    }
    val welcome = object : NavigationCommand {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
        override val destination: String
            get() = Destination.Welcome
    }

    object OnBoarding {
        val root = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.OnBoarding.Root
        }
        val currency = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.OnBoarding.Currency
        }
        val balance = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.OnBoarding.Balance
        }
        val monthlyTransactions = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = listOf(
                    navArgument("isIncome") { type = NavType.BoolType }
                )
            override val destination: String
                get() = Destination.OnBoarding.MonthlyTransactions
        }
        val savings = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.OnBoarding.Savings
        }
        val newTransaction = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = listOf(
                    navArgument("isExpense") { type = NavType.BoolType }
                )
            override val destination: String
                get() = Destination.OnBoarding.NewTransaction
        }
    }

    object Main {
        val root = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.Main.Root
        }
        val budget = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.Main.Budget.Budget
        }
        val newTransactions = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = listOf(
                    navArgument("isPayment") { type = NavType.BoolType }
                )
            override val destination: String
                get() = Destination.Main.Budget.NewTransaction
        }
        val transactions = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.Main.Budget.Transactions
        }
        val wallet = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.Main.Wallet.Wallet
        }
        val categoryLimit = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.Main.Wallet.CategoryLimit
        }
        val monthlyTransactions = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = listOf(
                    navArgument("page") { type = NavType.IntType }
                )
            override val destination: String
                get() = Destination.Main.Wallet.MonthlyTransactions
        }
        val newMonthlyTransaction = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = listOf(
                    navArgument("isExpense") { type = NavType.BoolType }
                )
            override val destination: String
                get() = Destination.Main.Wallet.NewMonthlyTransaction
        }
        val statistics = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.Main.Statistics.Statistics
        }
        val categoryStatistics = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.Main.Statistics.Category
        }
    }
}