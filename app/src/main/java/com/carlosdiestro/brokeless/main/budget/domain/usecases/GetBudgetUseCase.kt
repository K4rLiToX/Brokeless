package com.carlosdiestro.brokeless.main.budget.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import com.carlosdiestro.brokeless.main.budget.ui.models.BudgetPLO
import com.carlosdiestro.brokeless.utils.mappers.toPLO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBudgetUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    operator fun invoke(): Flow<BudgetPLO> = userPreferencesRepository.budget().toPLO()
}