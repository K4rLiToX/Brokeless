package com.carlosdiestro.brokeless.onboarding.ui.balance

data class OnBoardingBalanceState(
    val isBalanceCorrect: Boolean = true
)

sealed interface OnBoardingBalanceEvent {
    class ValidateBalance(val value: String) : OnBoardingBalanceEvent
}