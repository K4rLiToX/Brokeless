package com.carlosdiestro.brokeless.budget.ui

import androidx.compose.ui.graphics.Color
import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.core.ui.theme.Green
import com.carlosdiestro.brokeless.core.ui.theme.Red40

class TransactionPLO(
    val id: Int,
    val concept: String,
    val description: String,
    val category: CategoryPLO,
    val date: String,
    private val quantity: Double,
    private val currency: CurrencyPLO
) {
    val colorState: Color
        get() {
            return if(quantity > 0) Green else Red40
        }
    val quantityText: String
        get() {
            return if (currency.goesFirst) "${currency.symbol}$quantity" else "$quantity${currency.symbol}"
        }
}