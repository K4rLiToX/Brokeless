package com.carlosdiestro.brokeless.main.statistics.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.brokeless.core.domain.usecases.GetCurrencyUseCase
import com.carlosdiestro.brokeless.main.statistics.domain.usecases.GetCategoriesWithBalanceUseCase
import com.carlosdiestro.brokeless.main.statistics.domain.usecases.GetCurrentPeriodUseCase
import com.carlosdiestro.brokeless.main.statistics.domain.usecases.GetPeriodBalance
import com.carlosdiestro.brokeless.utils.mappers.toPLO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getPeriodBalance: GetPeriodBalance,
    private val getCategoriesWithBalanceUseCase: GetCategoriesWithBalanceUseCase,
    private val getCurrentPeriodUseCase: GetCurrentPeriodUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<StatisticsState> = MutableStateFlow(StatisticsState())
    val state = _state.asStateFlow()

    init {
        fetchCurrency()
        fetchCategoriesWithBalance()
        fetchPeriodBalance()
        fetchPeriod()
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

    private fun fetchCategoriesWithBalance() {
        viewModelScope.launch {
            getCategoriesWithBalanceUseCase().collect { response ->
                _state.update {
                    it.copy(
                        categories = response
                    )
                }
            }
        }
    }

    private fun fetchPeriodBalance() {
        viewModelScope.launch {
            getPeriodBalance().collect { (expenses, incomes) ->
                _state.update {
                    it.copy(
                        periodExpenses = expenses,
                        periodIncomes = incomes
                    )
                }
            }
        }
    }

    private fun fetchPeriod() {
        viewModelScope.launch {
            getCurrentPeriodUseCase().collect { response ->
                _state.update {
                    it.copy(
                        period = response.toPLO()
                    )
                }
            }
        }
    }
}