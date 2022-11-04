package com.carlosdiestro.brokeless.main.statistics.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import com.carlosdiestro.brokeless.main.transactions.domain.models.Period
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentPeriodUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {

    operator fun invoke(): Flow<Period> = repository.period()
}