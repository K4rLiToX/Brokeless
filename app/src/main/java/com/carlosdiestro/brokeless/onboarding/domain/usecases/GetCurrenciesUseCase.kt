package com.carlosdiestro.brokeless.onboarding.domain.usecases

import com.carlosdiestro.brokeless.core.domain.models.Currency
import com.carlosdiestro.brokeless.onboarding.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(): Flow<List<Currency>> = repository.getAll()
}