package com.carlosdiestro.brokeless.core.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.utils.mappers.toPLO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {

    operator fun invoke(): Flow<CurrencyPLO> = repository.currency().toPLO()
}