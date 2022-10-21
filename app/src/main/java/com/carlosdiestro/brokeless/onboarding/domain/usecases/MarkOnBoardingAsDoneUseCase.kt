package com.carlosdiestro.brokeless.onboarding.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class MarkOnBoardingAsDoneUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke() = userPreferencesRepository.updateFirstTime()
}