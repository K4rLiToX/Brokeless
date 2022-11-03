package com.carlosdiestro.brokeless.main.wallet.ui.monthly_transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.brokeless.core.domain.usecases.GetCurrencyUseCase
import com.carlosdiestro.brokeless.main.wallet.domain.usecases.GetMonthlyTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyTransactionsViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getMonthlyTransactionsUseCase: GetMonthlyTransactionsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val page = savedStateHandle.get<Int>("page")!!

    private var _state: MutableStateFlow<MonthlyTransactionsState> =
        MutableStateFlow(MonthlyTransactionsState(page = page))
    val state = _state.asStateFlow()

    init {
        fetchData()
    }

    fun onEvent(event: MonthlyTransactionsEvent) {
        when (event) {
            MonthlyTransactionsEvent.ChangePage -> changePage()
        }
    }

    private fun changePage() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    page = if (state.value.page == 0) 1 else 0,
                )
            }
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            val currencyFlow = getCurrencyUseCase.invoke()
            val monthlyTransactionsFlow = getMonthlyTransactionsUseCase.invoke()

            currencyFlow.combine(monthlyTransactionsFlow) { currency, transactions ->
                Pair(currency, transactions)
            }.collect { response ->
                _state.update {
                    it.copy(
                        currency = response.first,
                        transactions = response.second
                    )
                }
            }
        }
    }
}