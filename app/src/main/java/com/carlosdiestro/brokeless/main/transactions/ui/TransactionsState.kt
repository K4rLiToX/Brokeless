package com.carlosdiestro.brokeless.main.transactions.ui

import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.main.budget.ui.models.TransactionPLO
import com.carlosdiestro.brokeless.main.transactions.ui.models.PeriodPLO

data class TransactionsState(
    val transactions: List<TransactionPLO> = emptyList(),
    val currency: CurrencyPLO? = null,
    val periods: List<PeriodPLO> = emptyList(),
    val currentPeriod: PeriodPLO? = null,
    val filter: Int = 0
)

sealed interface TransactionsEvent {
    object ChangeFilter : TransactionsEvent
    class UpdatePeriod(val period: PeriodPLO) : TransactionsEvent
}