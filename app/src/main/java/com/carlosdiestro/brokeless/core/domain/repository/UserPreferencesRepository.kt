package com.carlosdiestro.brokeless.core.domain.repository

import com.carlosdiestro.brokeless.core.domain.models.Currency
import com.carlosdiestro.brokeless.main.budget.domain.models.Budget
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun isFirstTime(): Flow<Boolean>
    suspend fun updateFirstTime()

    fun totalBalance(): Flow<Double>
    suspend fun updateTotalBalance(value: Double)

    fun savingsPercentage(): Flow<Double>
    suspend fun updateSavingsPercentage(value: Double)

    fun currency(): Flow<Currency>
    suspend fun updateCurrency(currency: Currency)

    fun available(): Flow<Double>
    suspend fun updateAvailable(value: Double)

    fun savings(): Flow<Double>
    suspend fun updateSavings(value: Double)

    fun budget(): Flow<Budget>
    suspend fun updateCurrentBudget(value: Double)
    suspend fun updateTotalBudget(value: Double)

    fun period(): Flow<Long>
    suspend fun updatePeriod(date: String)
}