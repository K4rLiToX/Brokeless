package com.carlosdiestro.brokeless.core.navigation

object Destination {
    const val Root = "root"
    const val Welcome = "welcome_screen"

    object OnBoarding {
        const val Root = "on_boarding_root"
        const val Currency = "on_boarding_currency_screen"
        const val Balance = "on_boarding_balance_screen"
        const val MonthlyTransactions = "on_boarding_monthly_transactions"
        const val Savings = "on_boarding_savings_screen"
        const val NewTransaction = "on_boarding_new_transaction"
    }

    object Main {
        const val Root = "main_root"

        object Budget {
            const val Root = "budget_root"
            const val Budget = "budget_screen"
            const val Transactions = "budget_transactions"
            const val NewTransaction = "budget_new_transaction"
        }

        object Wallet {
            const val Root = "wallet_root"
            const val Wallet = "wallet_screen"
            const val CategoryLimit = "wallet_categories_limit"
            const val MonthlyTransactions = "wallet_monthly_transactions"
            const val NewMonthlyTransaction = "wallet_new_monthly_transactions"
        }

        object Statistics {
            const val Root = "statistics_root"
            const val Statistics = "statistics_screen"
            const val Category = "statistics_category_screen"
        }
    }
}