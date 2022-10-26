package com.carlosdiestro.brokeless.core.framework.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.carlosdiestro.brokeless.core.domain.models.Currency
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.AVAILABLE
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.BUDGET_CURRENT
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.BUDGET_TOTAL
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.CURRENCY_GOES_FIRST
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.CURRENCY_ICON
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.CURRENCY_ID
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.CURRENCY_NAME
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.CURRENCY_SYMBOL
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.IS_FIRST_TIME
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.PERIOD_END
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.PERIOD_ID
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.PERIOD_START
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.SAVINGS
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.SAVINGS_PERCENTAGE
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.TOTAL_BALANCE
import com.carlosdiestro.brokeless.main.budget.domain.models.Budget
import com.carlosdiestro.brokeless.main.transactions.domain.models.Period
import com.carlosdiestro.brokeless.utils.TimeManager
import com.carlosdiestro.brokeless.utils.round
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.userPreferences: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesService @Inject constructor(
    private val context: Context
) {
    private object UserPreferencesKeys {
        val IS_FIRST_TIME = booleanPreferencesKey("is_first_time")
        val TOTAL_BALANCE = doublePreferencesKey("total_balance")
        val SAVINGS_PERCENTAGE = doublePreferencesKey("savings_percentage")
        val CURRENCY_ID = intPreferencesKey("currency_id")
        val CURRENCY_NAME = stringPreferencesKey("currency_name")
        val CURRENCY_SYMBOL = stringPreferencesKey("currency_symbol")
        val CURRENCY_GOES_FIRST = booleanPreferencesKey("currency_goes_first")
        val CURRENCY_ICON = stringPreferencesKey("currency_icon")
        val AVAILABLE = doublePreferencesKey("available")
        val SAVINGS = doublePreferencesKey("savings")
        val BUDGET_TOTAL = doublePreferencesKey("budget_total")
        val BUDGET_CURRENT = doublePreferencesKey("budget_current")
        val PERIOD_ID = intPreferencesKey("period_id")
        val PERIOD_START = longPreferencesKey("period_start")
        val PERIOD_END = longPreferencesKey("period_end")
    }

    val isFirstTime: Flow<Boolean> =
        context.userPreferences.data.map { pref -> pref[IS_FIRST_TIME] ?: true }

    suspend fun updateFirstTime() {
        context.userPreferences.edit { pref ->
            pref[IS_FIRST_TIME] = false
        }
    }

    val totalBalance: Flow<Double> =
        context.userPreferences.data.map { pref -> pref[TOTAL_BALANCE] ?: 0.0 }

    suspend fun updateTotalBalance(value: Double) {
        context.userPreferences.edit { pref ->
            pref[TOTAL_BALANCE] = (pref[TOTAL_BALANCE] ?: 0.0) + value.round(2)
        }
    }

    val savingsPercentage: Flow<Double> =
        context.userPreferences.data.map { pref -> pref[SAVINGS_PERCENTAGE] ?: 15.0 }

    suspend fun updateSavingsPercentage(value: Double) {
        context.userPreferences.edit { pref ->
            pref[SAVINGS_PERCENTAGE] = value
        }
    }

    val currency: Flow<Currency> = context.userPreferences.data.map { pref ->
        val id = pref[CURRENCY_ID] ?: 1
        val name = pref[CURRENCY_NAME] ?: "EUR"
        val symbol = pref[CURRENCY_SYMBOL] ?: "€"
        val goesFirst = pref[CURRENCY_GOES_FIRST] ?: false
        val iconId = pref[CURRENCY_ICON] ?: "ic_euro"

        Currency(
            id,
            name,
            symbol,
            goesFirst,
            iconId
        )
    }

    suspend fun updateCurrency(currency: Currency) {
        context.userPreferences.edit { pref ->
            pref[CURRENCY_ID] = currency.id
            pref[CURRENCY_NAME] = currency.name
            pref[CURRENCY_SYMBOL] = currency.symbol
            pref[CURRENCY_GOES_FIRST] = currency.goesFirst
            pref[CURRENCY_ICON] = currency.iconId
        }
    }

    val available: Flow<Double> =
        context.userPreferences.data.map { pref -> pref[AVAILABLE] ?: 0.0 }

    suspend fun updateAvailable(value: Double) {
        context.userPreferences.edit { pref ->
            pref[AVAILABLE] = (pref[AVAILABLE] ?: 0.0) + value.round(2)
        }
    }

    val savings: Flow<Double> = context.userPreferences.data.map { pref -> pref[SAVINGS] ?: 0.0 }

    suspend fun updateSavings(value: Double) {
        context.userPreferences.edit { pref ->
            pref[SAVINGS] = (pref[SAVINGS] ?: 0.0) + value.round(2)
        }
    }

    val budget: Flow<Budget> = context.userPreferences.data.map { pref ->
        Budget(
            pref[BUDGET_TOTAL] ?: 0.0,
            pref[BUDGET_CURRENT] ?: 0.0
        )
    }

    suspend fun updateTotalBudget(value: Double) {
        context.userPreferences.edit { pref ->
            pref[BUDGET_TOTAL] = value.round(2)
        }
    }

    suspend fun updateCurrentBudget(value: Double) {
        context.userPreferences.edit { pref ->
            pref[BUDGET_CURRENT] = pref[BUDGET_CURRENT]!! + value.round(2)
        }
    }

    suspend fun resetCurrentBudget(value: Double) {
        context.userPreferences.edit { pref ->
            pref[BUDGET_CURRENT] = value.round(2)
        }
    }

    val period: Flow<Period> = context.userPreferences.data.map { pref ->
        Period(
            id = pref[PERIOD_ID] ?: -1,
            startDate = pref[PERIOD_START] ?: TimeManager.nowLong(),
            endDate = pref[PERIOD_END].takeIf { it != -1L }
        )
    }

    suspend fun finishPeriod() {
        context.userPreferences.edit { pref ->
            pref[PERIOD_END] = TimeManager.nowLong()
        }
    }

    suspend fun newPeriod(period: Period) {
        context.userPreferences.edit { pref ->
            pref[PERIOD_ID] = period.id
            pref[PERIOD_START] = period.startDate
            pref[PERIOD_END] = period.endDate ?: -1L
        }
    }
}