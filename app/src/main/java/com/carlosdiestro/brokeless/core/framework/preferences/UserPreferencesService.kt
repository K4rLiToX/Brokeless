package com.carlosdiestro.brokeless.core.framework.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService.UserPreferencesKeys.IS_FIRST_TIME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.userPreferences: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesService @Inject constructor(
    private val context: Context
) {
    private object UserPreferencesKeys {
        val IS_FIRST_TIME = booleanPreferencesKey("is_first_time")
    }

    val isFirstTime: Flow<Boolean> =
        context.userPreferences.data.map { pref -> pref[IS_FIRST_TIME] ?: true }

    suspend fun updateFirstTime() {
        context.userPreferences.edit { pref ->
            pref[IS_FIRST_TIME] = false
        }
    }
}