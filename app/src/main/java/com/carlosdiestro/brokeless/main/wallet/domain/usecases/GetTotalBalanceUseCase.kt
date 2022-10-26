package com.carlosdiestro.brokeless.main.wallet.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalBalanceUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {

    operator fun invoke(): Flow<Double> = repository.totalBalance()
}