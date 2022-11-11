package com.carlosdiestro.brokeless.main.transactions.domain.usecases

import com.carlosdiestro.brokeless.main.budget.ui.models.TransactionPLO
import com.carlosdiestro.brokeless.main.transactions.ui.models.PeriodPLO
import com.carlosdiestro.brokeless.utils.TimeManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetTransactionsByPeriodUseCase @Inject constructor() {

    operator fun invoke(
        transactions: List<TransactionPLO>,
        period: PeriodPLO
    ): Flow<List<TransactionPLO>> {
        val isCurrentPeriod = period.endDate == ""

        return flowOf(
            transactions.filter { transaction ->
                val firstCondition =
                    TimeManager.toLongDate(transaction.date) >= TimeManager.toLongDate(
                        period.startDate
                    )

                if (isCurrentPeriod) firstCondition
                else {
                    firstCondition &&
                            TimeManager.toLongDate(transaction.date) < TimeManager.toLongDate(
                        period.endDate
                    )
                }
            }
        )
    }
}