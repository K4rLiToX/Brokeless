package com.carlosdiestro.brokeless.main.budget.ui

import androidx.compose.ui.graphics.Color
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.core.ui.theme.Blue10
import com.carlosdiestro.brokeless.main.budget.ui.models.BudgetPLO
import com.carlosdiestro.brokeless.main.budget.ui.models.TransactionPLO

data class BudgetState(
    val budget: BudgetPLO = BudgetPLO(0.0, 0.0),
    val currency: CurrencyPLO? = null,
    val budgetColorState: Color = Blue10,
    val lastTransactions: List<TransactionPLO> = emptyList()
)