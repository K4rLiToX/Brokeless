package com.carlosdiestro.brokeless.main.statistics.domain.usecases

import com.carlosdiestro.brokeless.main.budget.domain.repository.TransactionRepository
import com.carlosdiestro.brokeless.main.statistics.ui.models.CategoryStatisticsPLO
import com.carlosdiestro.brokeless.utils.TimeManager
import com.carlosdiestro.brokeless.utils.expenses
import com.carlosdiestro.brokeless.utils.incomes
import com.carlosdiestro.brokeless.utils.mappers.toPLO
import com.carlosdiestro.brokeless.utils.total
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCategoriesWithBalanceUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val getCurrentPeriodUseCase: GetCurrentPeriodUseCase,
) {

    operator fun invoke(): Flow<List<CategoryStatisticsPLO>> = transactionRepository.getAll()
        .combine(getCurrentPeriodUseCase()) { transactions, period ->
            transactions
                .toPLO()
                .filter { TimeManager.toLongDate(it.date) >= period.startDate }
                .groupBy { it.category }
        }.map { map ->
            map.map { (category, transactions) ->
                CategoryStatisticsPLO(
                    category = category,
                    spent = transactions.expenses().total(),
                    received = transactions.incomes().total()
                )
            }
        }
}