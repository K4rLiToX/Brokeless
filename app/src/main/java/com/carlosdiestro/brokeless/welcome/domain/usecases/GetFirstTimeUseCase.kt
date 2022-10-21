package com.carlosdiestro.brokeless.welcome.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFirstTimeUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {

    operator fun invoke(): Flow<Boolean> = repository.isFirstTime()
}