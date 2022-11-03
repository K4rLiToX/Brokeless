package com.carlosdiestro.brokeless.main.wallet.ui.category_limit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdiestro.brokeless.core.domain.usecases.GetCurrencyUseCase
import com.carlosdiestro.brokeless.main.wallet.domain.usecases.GetCategoryUseCase
import com.carlosdiestro.brokeless.main.wallet.domain.usecases.UpdateCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val NO_LIMIT = "No Limit"

@HiltViewModel
class CategoryLimitViewModel @Inject constructor(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _state: MutableStateFlow<CategoryLimitState> =
        MutableStateFlow(CategoryLimitState())
    val state = _state.asStateFlow()

    init {
        getCategory()
        getCurrency()
    }

    fun onEvent(event: CategoryLimitEvent) {
        when (event) {
            CategoryLimitEvent.NoLimit        -> updateLimit()
            CategoryLimitEvent.SubmitLimit    -> submitCategory()
            is CategoryLimitEvent.UpdateLimit -> updateLimit(event.value)
        }
    }

    private fun submitCategory() {
        viewModelScope.launch {
            updateCategoryUseCase(
                state.value.category!!.copy(
                    limit = if (state.value.limit == NO_LIMIT) 0.0 else state.value.limit.toDouble()
                )
            )
        }
    }

    private fun updateLimit(value: String = "") {
        viewModelScope.launch {
            when (value) {
                "delete" -> _state.update {
                    it.copy(
                        limit = it.limit.dropLast(1)
                    )
                }
                ""       -> _state.update {
                    it.copy(
                        limit = "No Limit"
                    )
                }
                else     -> _state.update {
                    it.copy(
                        limit = if (it.limit == NO_LIMIT) value else it.limit.plus(value)
                    )
                }
            }
        }
    }

    private fun getCategory() {
        viewModelScope.launch {
            getCategoryUseCase(savedStateHandle.get<Int>("id")!!).collect { response ->
                _state.update {
                    it.copy(
                        category = response,
                        limit = response.limit?.toString() ?: NO_LIMIT
                    )
                }
            }
        }
    }

    private fun getCurrency() {
        viewModelScope.launch {
            getCurrencyUseCase().collect { response ->
                _state.update {
                    it.copy(
                        currency = response
                    )
                }
            }
        }
    }
}