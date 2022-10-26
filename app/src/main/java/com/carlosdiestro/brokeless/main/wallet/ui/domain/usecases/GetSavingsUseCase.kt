package com.carlosdiestro.brokeless.main.wallet.ui.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavingsUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {

    operator fun invoke(): Flow<Double> = repository.savings()
}