package com.carlosdiestro.brokeless.di

import com.carlosdiestro.brokeless.welcome.data.respository.UserPreferencesRepositoryImpl
import com.carlosdiestro.brokeless.welcome.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindUserPreferencesRepository(repository: UserPreferencesRepositoryImpl): UserPreferencesRepository
}