package com.carlosdiestro.brokeless.core.data.repository

import com.carlosdiestro.brokeless.core.domain.models.Currency
import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesService: UserPreferencesService
) : UserPreferencesRepository {

    override fun isFirstTime(): Flow<Boolean> = userPreferencesService.isFirstTime
    override suspend fun updateFirstTime() = userPreferencesService.updateFirstTime()

    override fun totalBalance(): Flow<Double> = userPreferencesService.totalBalance
    override suspend fun updateTotalBalance(value: Double) =
        userPreferencesService.updateTotalBalance(value)

    override fun savingsPercentage(): Flow<Double> = userPreferencesService.savingsPercentage
    override suspend fun updateSavingsPercentage(value: Double) =
        userPreferencesService.updateSavingsPercentage(value)

    override fun currency(): Flow<Currency> = userPreferencesService.currency
    override suspend fun updateCurrency(currency: Currency) =
        userPreferencesService.updateCurrency(currency)

    override fun available(): Flow<Double> = userPreferencesService.available
    override suspend fun updateAvailable(value: Double) =
        userPreferencesService.updateAvailable(value)

    override fun savings(): Flow<Double> = userPreferencesService.savings
    override suspend fun updateSavings(value: Double) = userPreferencesService.updateSavings(value)
}