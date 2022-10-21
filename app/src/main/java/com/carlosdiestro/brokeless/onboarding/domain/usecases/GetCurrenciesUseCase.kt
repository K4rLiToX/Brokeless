package com.carlosdiestro.brokeless.onboarding.domain.usecases

import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.onboarding.domain.repository.CurrencyRepository
import com.carlosdiestro.brokeless.utils.mappers.toPLO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {

    operator fun invoke(): Flow<List<CurrencyPLO>> = repository.getAll().toPLO()
}