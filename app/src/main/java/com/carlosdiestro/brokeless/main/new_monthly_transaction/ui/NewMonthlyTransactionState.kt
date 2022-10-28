package com.carlosdiestro.brokeless.main.new_monthly_transaction.ui

import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO

data class NewMonthlyTransactionState(
    val currency: CurrencyPLO? = null,
    val quantity: String = "0.0",
    val categories: List<CategoryPLO> = emptyList(),
    val selectedCategory: CategoryPLO? = null,
    val concept: String = "",
    val description: String = "",
    val currentPage: Int = 1,
    val isTotalQuantityValid: Boolean = false,
    val isAdditionalInfoValid: Boolean = false,
)

sealed interface NewMonthlyTransactionEvent {
    class UpdateQuantity(val value: String) : NewMonthlyTransactionEvent
    object ChangePage : NewMonthlyTransactionEvent
    class UpdateCategory(val category: CategoryPLO) : NewMonthlyTransactionEvent
    class UpdateConcept(val concept: String) : NewMonthlyTransactionEvent
    class UpdateDescription(val description: String) : NewMonthlyTransactionEvent
    class ValidateTotalQuantity(val value: String) : NewMonthlyTransactionEvent
    class ValidateConcept(val value: String) : NewMonthlyTransactionEvent
    class SubmitTransaction(val transaction: MonthlyTransactionPLO) : NewMonthlyTransactionEvent
}