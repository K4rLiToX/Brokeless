package com.carlosdiestro.brokeless.core.data.repository

import com.carlosdiestro.brokeless.core.domain.models.Currency
import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService
import com.carlosdiestro.brokeless.main.budget.domain.models.Budget
import com.carlosdiestro.brokeless.utils.TimeManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val service: UserPreferencesService
) : UserPreferencesRepository {

    override fun isFirstTime(): Flow<Boolean> = service.isFirstTime
    override suspend fun updateFirstTime() = service.updateFirstTime()

    override fun totalBalance(): Flow<Double> = service.totalBalance
    override suspend fun updateTotalBalance(value: Double) =
        service.updateTotalBalance(value)

    override fun savingsPercentage(): Flow<Double> = service.savingsPercentage
    override suspend fun updateSavingsPercentage(value: Double) =
        service.updateSavingsPercentage(value)

    override fun currency(): Flow<Currency> = service.currency
    override suspend fun updateCurrency(currency: Currency) =
        service.updateCurrency(currency)

    override fun available(): Flow<Double> = service.available
    override suspend fun updateAvailable(value: Double) =
        service.updateAvailable(value)

    override fun savings(): Flow<Double> = service.savings
    override suspend fun updateSavings(value: Double) = service.updateSavings(value)

    override fun budget(): Flow<Budget> = service.budget
    override suspend fun updateCurrentBudget(value: Double) =
        service.updateCurrentBudget(value)

    override suspend fun updateTotalBudget(value: Double) =
        service.updateTotalBudget(value)

    override fun period(): Flow<Long> = service.period
    override suspend fun updatePeriod(date: String) = service.updatePeriod(
        TimeManager.toLongDate(date)
    )
}