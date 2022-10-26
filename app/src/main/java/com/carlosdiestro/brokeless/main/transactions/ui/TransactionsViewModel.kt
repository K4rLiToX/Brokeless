package com.carlosdiestro.brokeless.main.transactions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.brokeless.core.domain.usecases.GetCurrencyUseCase
import com.carlosdiestro.brokeless.main.budget.domain.usecases.GetTransactionsUseCase
import com.carlosdiestro.brokeless.main.transactions.domain.usecases.GetPeriodsUseCase
import com.carlosdiestro.brokeless.main.transactions.ui.models.PeriodPLO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getPeriodsUseCase: GetPeriodsUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<TransactionsState> = MutableStateFlow(TransactionsState())
    val state = _state.asStateFlow()

    init {
        fetchCurrency()
        fetchPeriods()
        fetchTransactions()
    }

    fun onEvent(event: TransactionsEvent) {
        when (event) {
            TransactionsEvent.ChangeFilter    -> updateFilter()
            is TransactionsEvent.UpdatePeriod -> updatePeriod(event.period)
        }
    }

    private fun updateFilter() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    filter = if (state.value.filter == 0) 1 else 0
                )
            }
        }
    }

    private fun updatePeriod(period: PeriodPLO) {
        viewModelScope.launch {
            getTransactionsUseCase(period).collect { response ->
                _state.update {
                    it.copy(
                        transactions = response,
                        currentPeriod = period
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

    private fun fetchPeriods() {
        viewModelScope.launch {
            getPeriodsUseCase().collect { response ->
                _state.update {
                    it.copy(
                        periods = response
                    )
                }
            }
        }
    }

    private fun fetchTransactions() {
        viewModelScope.launch {
            getTransactionsUseCase().collect { response ->
                _state.update {
                    it.copy(
                        transactions = response
                    )
                }
            }
        }
    }
}