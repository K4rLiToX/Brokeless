package com.carlosdiestro.brokeless.main.wallet.ui.category_limit

import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO

data class CategoryLimitState(
    val currency: CurrencyPLO? = null,
    val category: CategoryPLO? = null,
    val limit: String = category?.limit?.toString() ?: "No Limit"
)

sealed interface CategoryLimitEvent {
    object NoLimit : CategoryLimitEvent
    class UpdateLimit(val value: String) : CategoryLimitEvent
    object SubmitLimit : CategoryLimitEvent
}