package com.carlosdiestro.brokeless.onboarding

import androidx.lifecycle.ViewModel
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(

) : ViewModel() {

    private var _state: MutableStateFlow<OnBoardingState> = MutableStateFlow(OnBoardingState())
    val state = _state.asStateFlow()

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.UpdateSelectedCurrency -> updateCurrency(event.currency)
            is OnBoardingEvent.UpdateTotalBalance     -> updateTotalBalance(event.balance)
        }
    }

    private fun updateCurrency(currency: CurrencyPLO) {
        _state.update {
            it.copy(
                selectedCurrency = currency
            )
        }
    }

    private fun updateTotalBalance(balance: String) {
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