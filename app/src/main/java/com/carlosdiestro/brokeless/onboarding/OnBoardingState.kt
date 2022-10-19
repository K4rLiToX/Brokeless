package com.carlosdiestro.brokeless.onboarding

import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO

data class OnBoardingState(
    val selectedCurrency: CurrencyPLO? = null,
    val totalBalance: String = "0.0",
    val fixedTransactions: List<MonthlyTransactionPLO> = emptyList()
)

sealed interface OnBoardingEvent {
    class UpdateSelectedCurrency(val currency: CurrencyPLO): OnBoardingEvent
    class UpdateTotalBalance(val balance: String): OnBoardingEvent
    class UpdateFixedTransactions(val fixedTransaction: MonthlyTransactionPLO): OnBoardingEvent
}
