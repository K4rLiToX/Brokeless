package com.carlosdiestro.brokeless.onboarding.ui.new_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.brokeless.core.domain.usecases.GetCategoriesUseCase
import com.carlosdiestro.brokeless.core.domain.usecases.GetCurrencyUseCase
import com.carlosdiestro.brokeless.core.domain.usecases.ValidateQuantityUseCase
import com.carlosdiestro.brokeless.core.domain.usecases.ValidateTextUseCase
import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.main.budget.ui.models.TransactionPLO
import com.carlosdiestro.brokeless.main.new_transaction.domain.usecases.InsertTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewTransactionViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val validateQuantityUseCase: ValidateQuantityUseCase,
    private val validateTextUseCase: ValidateTextUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<NewTransactionState> = MutableStateFlow(
        NewTransactionState()
    )
    val state = _state.asStateFlow()

    init {
        fetchCategories()
        fetchCurrency()
    }

    fun onEvent(event: NewTransactionEvent) {
        when (event) {
            NewTransactionEvent.ChangePage               -> changePage()
            NewTransactionEvent.EnableDateModification   -> enableDateModification()
            is NewTransactionEvent.UpdateCategory        -> updateCategory(event.category)
            is NewTransactionEvent.UpdateConcept         -> updateConcept(event.concept)
            is NewTransactionEvent.UpdateDate            -> updateDate(event.date)
            is NewTransactionEvent.UpdateDescription     -> updateDescription(event.description)
            is NewTransactionEvent.UpdateQuantity        -> updateQuantity(event.value)
            is NewTransactionEvent.ValidateConcept       -> validateQuantity(event.value)
            is NewTransactionEvent.ValidateTotalQuantity -> validateConcept(event.value)
            is NewTransactionEvent.SubmitTransaction     -> submitTransaction(event.transaction)
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collect { response ->
                _state.update {
                    it.copy(
                        categories = response
                    )
                }
            }
        }
    }

    private fun fetchCurrency() {
        viewModelScope.launch {
            getCurrencyUseCase().collect { response ->
                _state.update {
                    it.copy(
                        currency = response
                    )
                }
            }
        }
    }

    private fun changePage() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    currentPage = if (it.currentPage == 1) 2 else 1
                )
            }
        }
    }

    private fun enableDateModification() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isDateModificationEnabled = !it.isDateModificationEnabled
                )
            }
        }
    }

    private fun updateCategory(category: CategoryPLO) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedCategory = category
                )
            }
        }
    }

    private fun updateConcept(concept: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    concept = concept
                )
            }
            validateConcept(state.value.concept)
        }
    }

    private fun updateDate(date: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    date = date
                )
            }
        }
    }

    private fun updateDescription(description: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    description = description
                )
            }
        }
    }

    private fun updateQuantity(value: String) {
        viewModelScope.launch {
            when (value) {
                "delete" -> _state.update {
                    it.copy(
                        quantity = it.quantity.dropLast(1)
                    )
                }
                else     -> _state.update {
                    if (it.quantity == "0.0") {
                        it.copy(
                            quantity = value
                        )
                    } else {
                        it.copy(
                            quantity = it.quantity.plus(value)
                        )
                    }
                }
            }
            validateQuantity(state.value.quantity)
        }
    }

    private fun validateQuantity(value: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isTotalQuantityValid = validateQuantityUseCase(value, false)
                )
            }
        }
    }

    private fun validateConcept(value: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isAdditionalInfoValid = validateTextUseCase(value)
                )
            }
        }
    }

    private fun submitTransaction(transaction: TransactionPLO) {
        viewModelScope.launch {
            insertTransactionUseCase(transaction)
        }
    }
}