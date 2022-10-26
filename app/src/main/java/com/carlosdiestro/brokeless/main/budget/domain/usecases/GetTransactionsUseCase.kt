package com.carlosdiestro.brokeless.main.budget.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import com.carlosdiestro.brokeless.main.budget.domain.repository.TransactionRepository
import com.carlosdiestro.brokeless.main.budget.ui.models.TransactionPLO
import com.carlosdiestro.brokeless.main.transactions.ui.models.PeriodPLO
import com.carlosdiestro.brokeless.utils.TimeManager
import com.carlosdiestro.brokeless.utils.mappers.toPLO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {

    operator fun invoke(period: PeriodPLO? = null): Flow<List<TransactionPLO>> = flow {
        val currentPeriod = period?.let { TimeManager.toLongDate(it.startDate, "d MMM") } ?: userPreferencesRepository.period().first().startDate
        transactionRepository
            .getByPeriod(TimeManager.toStringDate(currentPeriod))
            .toPLO()
            .collect {
                emit(it)
            }
    }
}