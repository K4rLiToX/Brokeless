package com.carlosdiestro.brokeless.onboarding.currency

import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO

data class OnBoardingCurrencyState(
    val currencies: List<CurrencyPLO> = emptyList()
)