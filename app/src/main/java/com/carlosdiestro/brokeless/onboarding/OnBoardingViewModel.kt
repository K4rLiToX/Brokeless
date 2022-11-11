package com.carlosdiestro.brokeless.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO
import com.carlosdiestro.brokeless.onboarding.domain.usecases.SetUpUseCases
import com.carlosdiestro.brokeless.utils.*
import com.carlosdiestro.brokeless.utils.mappers.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val setUpUseCases: SetUpUseCases
) : ViewModel() {

    private var _state: MutableStateFlow<OnBoardingState> = MutableStateFlow(OnBoardingState())
    val state = _state.asStateFlow()

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.UpdateSelectedCurrency -> updateCurrency(event.currency)
            is OnBoardingEvent.UpdateTotalBalance -> updateTotalBalance(event.balance)
            is OnBoardingEvent.UpdateFixedTransactions -> updateMonthlyTransactions(event.monthlyTransactions)
            OnBoardingEvent.FinishOnBoarding -> finishOnBoarding()
            is OnBoardingEvent.UpdateSavingsPercentage -> updateSavingsPercentage(event.percentage)
            is OnBoardingEvent.UpdateSavingsText -> updateSavingsText(event.value)
            OnBoardingEvent.SubmitCurrency -> saveCurrency()
            OnBoardingEvent.SubmitMonthlyTransactions -> saveMonthlyTransactions()
            OnBoardingEvent.SubmitTotalBalance -> saveTotalBalance()
        }
    }

    private fun updateCurrency(currency: CurrencyPLO) {
        launchAndUpdate(_state) {
            it.copy(selectedCurrency = currency)
        }
    }

    private fun updateTotalBalance(balance: String) {
        launchAndUpdate(_state) {
            when (balance) {
                "delete" -> it.copy(totalBalance = it.totalBalance.dropLast(1))
                else     -> {
                    if (it.totalBalance == "0.0") it.copy(totalBalance = balance)
                    else it.copy(totalBalance = it.totalBalance.plus(balance))
                }
            }
        }
    }

    private fun updateMonthlyTransactions(monthlyTransactions: MonthlyTransactionPLO) {
        launchAndUpdate(_state) {
            it.copy(
                monthlyTransactions = it.monthlyTransactions.plus(monthlyTransactions)
            )
        }
    }

    private fun finishOnBoarding() {
        viewModelScope.launch {
            setUpUseCases.UpdateFirstTimeUseCase().invoke()
            setUpUseCases.UpdateSavingsPercentageUseCase().invoke(state.value.savingsPercentage)
        }
    }

    private fun updateSavingsPercentage(percentage: Double) {
        launchAndUpdate(_state) {
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

    private fun updateSavingsText(value: String) {
        launchAndUpdate(_state) {
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

    private fun saveCurrency() {
        viewModelScope.launch {
            setUpUseCases.UpdateCurrencyUseCase().invoke(state.value.selectedCurrency!!.toDomain())
        }
    }

    private fun saveMonthlyTransactions() {
        viewModelScope.launch {
            setUpUseCases.UpdateMonthlyTransactionsUseCase().invoke(state.value.monthlyTransactions.toDomain())
        }
    }

    private fun saveTotalBalance() {
        viewModelScope.launch {
            setUpUseCases.UpdateTotalBalanceUseCase().invoke(state.value.totalBalance.toDouble())
            setUpUseCases.UpdateSavingsUseCase().invoke(state.value.totalBalance.toDouble())
        }
    }
}