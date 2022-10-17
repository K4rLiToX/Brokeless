package com.carlosdiestro.brokeless.welcome.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.brokeless.welcome.domain.usecases.GetFirstTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val isFirstTimeUseCase: GetFirstTimeUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<WelcomeState> = MutableStateFlow(WelcomeState())
    val state = _state.asStateFlow()

    init {
        fetchIsFirstTime()
    }

    private fun fetchIsFirstTime() {
        viewModelScope.launch {
            isFirstTimeUseCase().collect { response ->
                _state.update {
                    it.copy(
                        isFirstTime = response
                    )
                }
            }
        }
    }
}