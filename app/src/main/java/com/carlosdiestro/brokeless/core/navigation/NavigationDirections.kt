package com.carlosdiestro.brokeless.core.navigation

import androidx.navigation.NamedNavArgument

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
        val incomes = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.OnBoarding.Incomes
        }
        val expenses = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.OnBoarding.Expenses
        }
        val savings = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.OnBoarding.Savings
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
        val incomes = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.Main.Wallet.Incomes
        }
        val expenses = object : NavigationCommand {
            override val arguments: List<NamedNavArgument>
                get() = emptyList()
            override val destination: String
                get() = Destination.Main.Wallet.Expenses
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