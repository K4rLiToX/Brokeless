package com.carlosdiestro.brokeless.welcome.data.respository

import com.carlosdiestro.brokeless.core.ui.framework.UserPreferencesService
import com.carlosdiestro.brokeless.welcome.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesService: UserPreferencesService
) : UserPreferencesRepository {

    override fun isFirstTime(): Flow<Boolean> = userPreferencesService.isFirstTime
    override suspend fun updateFirstTime() = userPreferencesService.updateFirstTime()
}