package com.carlosdiestro.brokeless.onboarding

import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO

data class OnBoardingState(
    val selectedCurrency: CurrencyPLO? = null,
    val totalBalance: String = "0.0",
    val monthlyTransactions: List<MonthlyTransactionPLO> = emptyList(),
    val savingsPercentage: Double = 0.0,
    val savingsText: String = "0.0"
)

sealed interface OnBoardingEvent {
    class UpdateSelectedCurrency(val currency: CurrencyPLO) : OnBoardingEvent
    class UpdateTotalBalance(val balance: String) : OnBoardingEvent
    class UpdateFixedTransactions(val monthlyTransactions: MonthlyTransactionPLO) : OnBoardingEvent
    class UpdateSavingsPercentage(val percentage: Double) : OnBoardingEvent
    class UpdateSavingsText(val value: String) : OnBoardingEvent
    object FinishOnBoarding : OnBoardingEvent
    object SubmitCurrency : OnBoardingEvent
    object SubmitTotalBalance : OnBoardingEvent
    object SubmitMonthlyTransactions : OnBoardingEvent
}
