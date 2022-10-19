package com.carlosdiestro.brokeless.onboarding.ui.new_transaction

import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO

data class OnBoardingNewTransactionState(
    val quantity: String = "0.0",
    val selectedCategory: CategoryPLO? = null,
    val categories: List<CategoryPLO> = emptyList(),
    val concept: String = "",
    val description: String = "",
    val date: String = "",
    val isDateModificationEnabled: Boolean  = false,
    val currentPage: Int = 1,
    val isTotalQuantityValid: Boolean = false,
    val isAdditionalInfoValid: Boolean = false
)

sealed interface OnBoardingNewTransactionEvent {
    class UpdateQuantity(val value: String) : OnBoardingNewTransactionEvent
    object ChangePage : OnBoardingNewTransactionEvent
    class UpdateCategory(val category: CategoryPLO) : OnBoardingNewTransactionEvent
    class UpdateConcept(val concept: String): OnBoardingNewTransactionEvent
    class UpdateDescription(val description: String) : OnBoardingNewTransactionEvent
    class UpdateDate(val date: String): OnBoardingNewTransactionEvent
    object EnableDateModification : OnBoardingNewTransactionEvent
    class ValidateTotalQuantity(val value: String) : OnBoardingNewTransactionEvent
    class ValidateConcept(val value: String) : OnBoardingNewTransactionEvent
}