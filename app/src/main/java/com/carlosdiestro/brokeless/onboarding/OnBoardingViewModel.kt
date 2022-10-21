package com.carlosdiestro.brokeless.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO
import com.carlosdiestro.brokeless.onboarding.domain.usecases.SaveInitialInformationUseCase
import com.carlosdiestro.brokeless.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val saveInitialInformationUseCase: SaveInitialInformationUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<OnBoardingState> = MutableStateFlow(OnBoardingState())
    val state = _state.asStateFlow()

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.UpdateSelectedCurrency -> updateCurrency(event.currency)
            is OnBoardingEvent.UpdateTotalBalance -> updateTotalBalance(event.balance)
            is OnBoardingEvent.UpdateFixedTransactions -> updateMonthlyTransactions(event.monthlyTransactions)
            OnBoardingEvent.FinishOnBoarding -> save()
            is OnBoardingEvent.UpdateSavingsPercentage -> updateSavingsPercentage(event.percentage)
            is OnBoardingEvent.UpdateSavingsText -> updateSavingsText(event.value)
        }
    }

    private fun updateCurrency(currency: CurrencyPLO) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedCurrency = currency
                )
            }
        }
    }

    private fun updateTotalBalance(balance: String) {
        viewModelScope.launch {
            when (balance) {
                "delete" -> _state.update {
                    it.copy(
                        totalBalance = it.totalBalance.dropLast(1)
                    )
                }
                else     -> _state.update {
                    if (it.totalBalance == "0.0") {
                        it.copy(
                            totalBalance = balance
                        )
                    } else {
                        it.copy(
                            totalBalance = it.totalBalance.plus(balance)
                        )
                    }
                }
            }
        }
    }

    private fun updateMonthlyTransactions(fixedTransaction: MonthlyTransactionPLO) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    monthlyTransactions = it.monthlyTransactions.plus(fixedTransaction)
                )
            }
        }
    }

    private fun save() {
        viewModelScope.launch {
            saveInitialInformationUseCase(
                totalBalance = state.value.totalBalance.toDouble(),
                currency = state.value.selectedCurrency!!,
                monthlyTransactions = state.value.monthlyTransactions,
                savingsPercentage = state.value.savingsPercentage
            )
        }
    }

    private fun updateSavingsPercentage(percentage: Double) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    savingsPercentage = percentage.round(2),
                    savingsText = (
                            percentage
                                .asDirectProportion()
                                .round(2) *
                                    (state.value.monthlyTransactions.incomes().total() +
                                            state.value.monthlyTransactions.expenses().total()
                                            )).round(2)
                        .toString()
                )
            }
        }
    }

    private fun updateSavingsText(value: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    savingsPercentage = (
                            value.ifBlank { "0.0" }
                                .toDouble() directProportion (
                                    state.value.monthlyTransactions.incomes().total() +
                                            state.value.monthlyTransactions.expenses().total()
                                    )).round(2)
                        .asPercentage()
                        .round(
                            0
                        ),
                    savingsText = value.ifBlank { "0.0" }
                )
            }
        }
    }
}