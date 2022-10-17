package com.carlosdiestro.brokeless.di

import android.content.Context
import com.carlosdiestro.brokeless.core.framework.UserPreferencesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FrameworkModule {

    /**
     * DataStore
     */

    @Singleton
    @Provides
    fun provideUserPreferencesService(@ApplicationContext context: Context): UserPreferencesService {
        return UserPreferencesService(context)
    }
}