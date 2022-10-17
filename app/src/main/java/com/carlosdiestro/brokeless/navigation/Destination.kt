package com.carlosdiestro.brokeless.navigation

object Destination {
    const val Welcome = "welcome_screen"

    object OnBoarding {
        const val Root = "on_boarding_root"
        const val Currency = "on_boarding_currency_screen"
        const val Balance = "on_boarding_balance_screen"
        const val Incomes = "on_boarding_incomes_screen"
        const val Expenses = "on_boarding_expenses_screen"
        const val Savings = "on_boarding_savings_screen"
    }

    object Main {
        const val Root = "main_root"

        object Budget {
            const val Root = "budget_root"
            const val Budget = "budget_screen"
            const val Transactions = "budget_transactions"
        }

        object Wallet {
            const val Root = "wallet_root"
            const val Wallet = "wallet_screen"
            const val CategoryLimit = "wallet_categories_limit"
            const val Incomes = "wallet_incomes"
            const val Expenses = "wallet_expenses"
        }

        object Statistics {
            const val Root = "statistics_root"
            const val Statistics = "statistics_screen"
            const val Category = "statistics_category_screen"
        }

        object NewTransaction {
            const val Root = "new_transaction_root"
            const val Quantity = "new_transaction_quantity"
            const val AdditionalInfo = "new_transaction_additional_info"
        }
    }
}