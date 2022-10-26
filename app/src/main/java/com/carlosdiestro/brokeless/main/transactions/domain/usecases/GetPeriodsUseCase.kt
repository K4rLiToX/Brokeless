package com.carlosdiestro.brokeless.main.transactions.domain.usecases

import com.carlosdiestro.brokeless.main.transactions.domain.repository.PeriodRepository
import com.carlosdiestro.brokeless.main.transactions.ui.models.PeriodPLO
import com.carlosdiestro.brokeless.utils.mappers.toPLO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPeriodsUseCase @Inject constructor(
    private val repository: PeriodRepository
) {

    operator fun invoke(): Flow<List<PeriodPLO>> = repository.getAll().toPLO()
}