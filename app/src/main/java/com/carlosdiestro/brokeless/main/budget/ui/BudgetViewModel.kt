package com.carlosdiestro.brokeless.main.budget.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.brokeless.core.domain.usecases.GetCurrencyUseCase
import com.carlosdiestro.brokeless.core.ui.theme.Green
import com.carlosdiestro.brokeless.core.ui.theme.Orange30
import com.carlosdiestro.brokeless.core.ui.theme.Red40
import com.carlosdiestro.brokeless.core.ui.theme.Red50
import com.carlosdiestro.brokeless.main.budget.domain.usecases.GetBudgetUseCase
import com.carlosdiestro.brokeless.main.budget.domain.usecases.GetTransactionsUseCase
import com.carlosdiestro.brokeless.main.budget.domain.usecases.StartNewPeriodUseCase
import com.carlosdiestro.brokeless.main.budget.ui.models.BudgetPLO
import com.carlosdiestro.brokeless.utils.directProportion
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
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val startNewPeriodUseCase: StartNewPeriodUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<BudgetState> = MutableStateFlow(BudgetState())
    val state = _state.asStateFlow()

    init {
        fetchCurrency()
        fetchBudget()
        fetchLastTransactions()
    }

    fun onEvent(event: BudgetEvent) {
        when(event) {
            BudgetEvent.NewPeriod -> newPeriod()
        }
    }

    private fun newPeriod() {
        viewModelScope.launch {
            startNewPeriodUseCase()
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

    private fun fetchBudget() {
        viewModelScope.launch {
            getBudgetUseCase().collect { response ->
                _state.update {
                    it.copy(
                        budget = response,
                        budgetColorState = getColorState(response)
                    )
                }
            }
        }
    }

    private fun fetchLastTransactions() {
        viewModelScope.launch {
            getTransactionsUseCase().collect { response ->
                _state.update {
                    it.copy(
                        lastTransactions = response.take(3)
                    )
                }
            }
        }
    }

    private fun getColorState(budget: BudgetPLO): Color {
        return when (budget.current directProportion budget.total) {
            in (0.75..1.0) -> Green
            in (0.25..0.74) -> Orange30
            in (0.10..0.24) -> Red40
            else -> Red50
        }
    }
}