package com.carlosdiestro.brokeless.main.wallet.ui.models

import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO

class MonthlyTransactionPLO(
    val id: Int,
    val concept: String,
    val category: CategoryPLO,
    val isActive: Boolean,
    private val quantity: Double,
    private val currency: CurrencyPLO
) {
    val quantityText: String
        get() {
            return if (currency.goesFirst) "${currency.symbol}$quantity" else "$quantity${currency.symbol}"
        }
}