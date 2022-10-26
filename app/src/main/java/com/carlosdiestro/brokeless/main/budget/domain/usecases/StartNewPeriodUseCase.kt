package com.carlosdiestro.brokeless.main.budget.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.MonthlyTransactionRepository
import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import com.carlosdiestro.brokeless.main.budget.domain.Transaction
import com.carlosdiestro.brokeless.main.budget.domain.repository.TransactionRepository
import com.carlosdiestro.brokeless.main.transactions.domain.models.Period
import com.carlosdiestro.brokeless.main.transactions.domain.repository.PeriodRepository
import com.carlosdiestro.brokeless.utils.TimeManager
import com.carlosdiestro.brokeless.utils.asDirectProportion
import com.carlosdiestro.brokeless.utils.mappers.toDomain
import com.carlosdiestro.brokeless.utils.mappers.toPLO
import com.carlosdiestro.brokeless.utils.round
import com.carlosdiestro.brokeless.utils.total
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class StartNewPeriodUseCase @Inject constructor(
    private val monthlyTransactionRepository: MonthlyTransactionRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val transactionRepository: TransactionRepository,
    private val periodRepository: PeriodRepository
) {

    suspend operator fun invoke() {
        // Get monthly transactions and savings percentage
        val monthlyTransactions = monthlyTransactionRepository.getAll().toPLO().first()
        val savingsPercentage = userPreferencesRepository.savingsPercentage().first()

        // Deduct expenses from incomes
        val afterExpenses = monthlyTransactions.total()

        // Calculate savings quantity
        val savingsQuantity = (savingsPercentage.asDirectProportion() * afterExpenses).round(2)

        // Calculate budget
        val budget = afterExpenses - savingsQuantity

        // Update current period enddate in preferences and db
        userPreferencesRepository.finishPeriod()
        val currentPeriodUpdated = userPreferencesRepository.period().first()
        periodRepository.update(currentPeriodUpdated)

        // Start new period
        val newPeriod = Period(
            id = -1,
            startDate = TimeManager.nowLong(),
            endDate = null
        )
        val newPeriodId = periodRepository.insert(newPeriod)
        userPreferencesRepository.newPeriod(newPeriod.copy(id = newPeriodId.toInt()))

        // Update total budget and reset former period current budget
        userPreferencesRepository.updateTotalBudget(budget)
        userPreferencesRepository.resetCurrentBudget(budget)

        // Add budget to total available and total balance
        userPreferencesRepository.updateAvailable(budget)
        userPreferencesRepository.updateTotalBudget(budget)

        // Add savings to total savings
        userPreferencesRepository.updateSavings(savingsQuantity)

        // Add monthly transactions to period transactions
        monthlyTransactions.map { transaction ->
            transactionRepository.insert(
                Transaction(
                    id = -1,
                    concept = transaction.concept,
                    description = "",
                    quantity = transaction.quantity,
                    category = transaction.category.toDomain(),
                    date = TimeManager.toStringDate(System.currentTimeMillis())
                )
            )
        }
    }
}