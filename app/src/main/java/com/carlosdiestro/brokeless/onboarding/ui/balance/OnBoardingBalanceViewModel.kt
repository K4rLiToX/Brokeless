package com.carlosdiestro.brokeless.onboarding.ui.balance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.brokeless.core.domain.usecases.ValidateQuantityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingBalanceViewModel @Inject constructor(
    private val validateQuantityUseCase: ValidateQuantityUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<OnBoardingBalanceState> = MutableStateFlow(OnBoardingBalanceState())
    val state = _state.asStateFlow()

    fun onEvent(event: OnBoardingBalanceEvent) {
        when(event) {
            is OnBoardingBalanceEvent.ValidateBalance -> validateQuantity(event.value)
        }
    }

    private fun validateQuantity(value: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isBalanceCorrect = validateQuantityUseCase(value)
                )
            }
        }
    }
}