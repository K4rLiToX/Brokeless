package com.carlosdiestro.brokeless.welcome.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun isFirstTime(): Flow<Boolean>
    suspend fun updateFirstTime()
}