package com.carlosdiestro.brokeless.main.budget.ui.models

import androidx.compose.ui.graphics.Color
import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.core.ui.theme.Green
import com.carlosdiestro.brokeless.core.ui.theme.Red40

class TransactionPLO(
    val id: Int,
    val concept: String,
    val description: String,
    val quantity: Double,
    val category: CategoryPLO,
    val date: String,
) {
    val colorState: Color
        get() {
            return if (quantity > 0) Green else Red40
        }
}