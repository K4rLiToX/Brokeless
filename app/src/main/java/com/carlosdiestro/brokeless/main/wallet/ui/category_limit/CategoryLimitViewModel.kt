package com.carlosdiestro.brokeless.main.wallet.ui.category_limit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.carlosdiestro.brokeless.core.domain.usecases.GetCurrencyUseCase
import com.carlosdiestro.brokeless.main.wallet.domain.usecases.GetCategoryUseCase
import com.carlosdiestro.brokeless.main.wallet.domain.usecases.UpdateCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoryLimitViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _state: MutableStateFlow<CategoryLimitState> = MutableStateFlow(CategoryLimitState())
    val state = _state.asStateFlow()

    init {
        getCategory()
        getCurrency()
    }

    fun onEvent(event: CategoryLimitEvent) {
        when(event) {
            CategoryLimitEvent.NoLimit        -> TODO()
            CategoryLimitEvent.SubmitLimit    -> submitCategory()
            is CategoryLimitEvent.UpdateLimit -> updateLimit(event.value)
        }
    }

    private fun submitCategory() {

    }

    private fun updateLimit(value: String) {

    }

    private fun getCategory() {

    }

    private fun getCurrency() {

    }
}