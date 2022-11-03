package com.carlosdiestro.brokeless.main.wallet.ui.monthly_transactions

import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO

data class MonthlyTransactionsState(
    val currency: CurrencyPLO? = null,
    val transactions: List<MonthlyTransactionPLO> = emptyList(),
    val page: Int
)

sealed interface MonthlyTransactionsEvent {
    object ChangePage : MonthlyTransactionsEvent
}