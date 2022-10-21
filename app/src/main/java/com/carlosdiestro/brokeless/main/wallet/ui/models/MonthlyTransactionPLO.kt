package com.carlosdiestro.brokeless.main.wallet.ui.models

import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO

class MonthlyTransactionPLO(
    val id: Int,
    val concept: String,
    val category: CategoryPLO,
    val isActive: Boolean,
    val quantity: Double
)