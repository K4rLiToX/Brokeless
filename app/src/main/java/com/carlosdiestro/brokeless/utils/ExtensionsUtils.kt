package com.carlosdiestro.brokeless.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

fun <T> ViewModel.launchAndCollect(
    flow: Flow<T>,
    body: (T) -> Unit
) {
    viewModelScope.launch {
        flow.collect(body)
    }
}

fun <T> ViewModel.launchAndUpdate(
    state: MutableStateFlow<T>,
    body: (T) -> T
) {
    viewModelScope.launch {
        state.update {
            body(it)
        }
    }
}