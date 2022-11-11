package com.carlosdiestro.brokeless.onboarding.ui.currency

import androidx.lifecycle.ViewModel
import com.carlosdiestro.brokeless.onboarding.domain.usecases.GetCurrenciesUseCase
import com.carlosdiestro.brokeless.utils.launchAndCollect
import com.carlosdiestro.brokeless.utils.mappers.toPLO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OnBoardingCurrencyViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<OnBoardingCurrencyState> = MutableStateFlow(
        OnBoardingCurrencyState()
    )
    val state = _state.asStateFlow()

    init {
        fetchCurrencies()
    }

    private fun fetchCurrencies() {
        launchAndCollect(getCurrenciesUseCase()) { response ->
            _state.update {
                it.copy(
                    currencies = response.toPLO()
                )
            }
        }
    }
}