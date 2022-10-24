package com.carlosdiestro.brokeless.onboarding.ui.new_transaction

import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.main.budget.ui.models.TransactionPLO

data class NewTransactionState(
    val quantity: String = "0.0",
    val selectedCategory: CategoryPLO? = null,
    val categories: List<CategoryPLO> = emptyList(),
    val concept: String = "",
    val description: String = "",
    val date: String = "",
    val isDateModificationEnabled: Boolean = false,
    val currentPage: Int = 1,
    val isTotalQuantityValid: Boolean = false,
    val isAdditionalInfoValid: Boolean = false,
    val currency: CurrencyPLO? = null,
)

sealed interface NewTransactionEvent {
    class UpdateQuantity(val value: String) : NewTransactionEvent
    object ChangePage : NewTransactionEvent
    class UpdateCategory(val category: CategoryPLO) : NewTransactionEvent
    class UpdateConcept(val concept: String) : NewTransactionEvent
    class UpdateDescription(val description: String) : NewTransactionEvent
    class UpdateDate(val date: String) : NewTransactionEvent
    object EnableDateModification : NewTransactionEvent
    class ValidateTotalQuantity(val value: String) : NewTransactionEvent
    class ValidateConcept(val value: String) : NewTransactionEvent
    class SubmitTransaction(val transaction: TransactionPLO) : NewTransactionEvent
}