package com.carlosdiestro.brokeless.statistics.domain.usecases

import com.carlosdiestro.brokeless.main.budget.domain.repository.TransactionRepository
import com.carlosdiestro.brokeless.utils.TimeManager
import com.carlosdiestro.brokeless.utils.expenses
import com.carlosdiestro.brokeless.utils.incomes
import com.carlosdiestro.brokeless.utils.mappers.toPLO
import com.carlosdiestro.brokeless.utils.total
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetPeriodBalance @Inject constructor(
    private val getCurrentPeriodUseCase: GetCurrentPeriodUseCase,
    private val repository: TransactionRepository
) {

    operator fun invoke(): Flow<Pair<Double, Double>> = getCurrentPeriodUseCase().combine(repository.getAll()) { period, transactions ->
        val currentTransactions = transactions
            .toPLO()
            .filter { TimeManager.toLongDate(it.date) == period.startDate }
        Pair(
            currentTransactions.expenses().total(),
            currentTransactions.incomes().total()
        )
    }
}