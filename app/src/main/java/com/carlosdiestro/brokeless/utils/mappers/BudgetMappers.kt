package com.carlosdiestro.brokeless.utils.mappers

import com.carlosdiestro.brokeless.main.budget.domain.models.Budget
import com.carlosdiestro.brokeless.main.budget.ui.models.BudgetPLO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Budget.toPLO(): BudgetPLO = BudgetPLO(
    total = total,
    current = current
)

fun Flow<Budget>.toPLO() = this.map { it.toPLO() }