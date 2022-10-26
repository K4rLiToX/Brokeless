package com.carlosdiestro.brokeless.main.wallet.ui

import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO

data class WalletState(
    val currency: CurrencyPLO? = null,
    val balance: Double = 0.0,
    val available: Double = 0.0,
    val savings: Double = 0.0,
    val incomes: Double = 0.0,
    val expenses: Double = 0.0,
    val categories: List<CategoryPLO> = emptyList(),
    val page: Int = 0
)

sealed interface WalletEvent {
    object ChangePage : WalletEvent
}