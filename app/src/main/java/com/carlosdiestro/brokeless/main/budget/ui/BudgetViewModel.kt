package com.carlosdiestro.brokeless.main.budget.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.brokeless.core.domain.usecases.GetCurrencyUseCase
import com.carlosdiestro.brokeless.main.budget.domain.usecases.GetBudgetUseCase
import com.carlosdiestro.brokeless.main.budget.domain.usecases.GetTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getBudgetUseCase: GetBudgetUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<BudgetState> = MutableStateFlow(BudgetState())
    val state = _state.asStateFlow()

    init {
        fetchCurrency()
        fetchBudget()
        fetchLastTransactions()
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

    private fun fetchBudget() {

    }

    private fun fetchLastTransactions() {

    }
}