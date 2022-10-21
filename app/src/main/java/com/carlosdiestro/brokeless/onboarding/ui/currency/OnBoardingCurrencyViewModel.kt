package com.carlosdiestro.brokeless.onboarding.ui.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.brokeless.onboarding.domain.usecases.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            getCurrenciesUseCase().collect { response ->
                _state.update {
                    it.copy(
                        currencies = response
                    )
                }
            }
        }
    }
}