package com.carlosdiestro.brokeless.onboarding

import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO

data class OnBoardingState(
    val selectedCurrency: CurrencyPLO? = null
)

sealed interface OnBoardingEvent {
    class UpdateSelectedCurrency(val currency: CurrencyPLO): OnBoardingEvent
}
