package com.carlosdiestro.brokeless.statistics.ui

import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.main.transactions.ui.models.PeriodPLO
import com.carlosdiestro.brokeless.statistics.ui.models.CategoryStatisticsPLO

data class StatisticsState(
    val currency: CurrencyPLO? = null,
    val categories: List<CategoryStatisticsPLO> = emptyList(),
    val periodExpenses: Double = 0.0,
    val periodIncomes: Double = 0.0,
    val period: PeriodPLO? = null,
)