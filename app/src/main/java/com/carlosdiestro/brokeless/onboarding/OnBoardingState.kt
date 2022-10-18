package com.carlosdiestro.brokeless.onboarding

import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO

data class OnBoardingState(
    val selectedCurrency: CurrencyPLO? = null,
    val totalBalance: String = "0.0"
)

sealed interface OnBoardingEvent {
    class UpdateSelectedCurrency(val currency: CurrencyPLO): OnBoardingEvent
    class UpdateTotalBalance(val balance: String): OnBoardingEvent
}
