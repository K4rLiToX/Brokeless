package com.carlosdiestro.brokeless.main.wallet.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.brokeless.core.domain.usecases.GetCategoriesUseCase
import com.carlosdiestro.brokeless.core.domain.usecases.GetCurrencyUseCase
import com.carlosdiestro.brokeless.main.wallet.ui.domain.usecases.GetAvailableUseCase
import com.carlosdiestro.brokeless.main.wallet.ui.domain.usecases.GetMonthlyTransactionsUseCase
import com.carlosdiestro.brokeless.main.wallet.ui.domain.usecases.GetSavingsUseCase
import com.carlosdiestro.brokeless.main.wallet.ui.domain.usecases.GetTotalBalanceUseCase
import com.carlosdiestro.brokeless.utils.expenses
import com.carlosdiestro.brokeless.utils.incomes
import com.carlosdiestro.brokeless.utils.total
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getTotalBalanceUseCase: GetTotalBalanceUseCase,
    private val getAvailableUseCase: GetAvailableUseCase,
    private val getSavingsUseCase: GetSavingsUseCase,
    private val getMonthlyTransactionsUseCase: GetMonthlyTransactionsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<WalletState> = MutableStateFlow(WalletState())
    val state = _state.asStateFlow()

    init {
        fetchCurrency()
        fetchTotalBalance()
        fetchAvailable()
        fetchSavings()
        fetchMonthlyTransactions()
        fetchCategories()
    }

    fun onEvent(event: WalletEvent) {
        when (event) {
            WalletEvent.ChangePage -> changePage()
        }
    }

    private fun changePage() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    page = if (state.value.page == 0) 1 else 0
                )
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

    private fun fetchTotalBalance() {
        viewModelScope.launch {
            getTotalBalanceUseCase().collect { response ->
                _state.update {
                    it.copy(
                        balance = response
                    )
                }
            }
        }
    }

    private fun fetchAvailable() {
        viewModelScope.launch {
            getAvailableUseCase().collect { response ->
                _state.update {
                    it.copy(
                        available = response
                    )
                }
            }
        }
    }

    private fun fetchSavings() {
        viewModelScope.launch {
            getSavingsUseCase().collect { response ->
                _state.update {
                    it.copy(
                        savings = response
                    )
                }
            }
        }
    }

    private fun fetchMonthlyTransactions() {
        viewModelScope.launch {
            getMonthlyTransactionsUseCase().collect { response ->
                _state.update {
                    it.copy(
                        incomes = response.incomes().total(),
                        expenses = response.expenses().total()
                    )
                }
            }
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
}