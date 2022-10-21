package com.carlosdiestro.brokeless.di

import com.carlosdiestro.brokeless.core.data.repository.CategoryRepositoryImpl
import com.carlosdiestro.brokeless.core.data.repository.CurrencyRepositoryImpl
import com.carlosdiestro.brokeless.core.data.repository.MonthlyTransactionRepositoryImpl
import com.carlosdiestro.brokeless.core.data.repository.UserPreferencesRepositoryImpl
import com.carlosdiestro.brokeless.core.domain.repository.CategoryRepository
import com.carlosdiestro.brokeless.core.domain.repository.MonthlyTransactionRepository
import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import com.carlosdiestro.brokeless.onboarding.domain.repository.CurrencyRepository
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

    @Singleton
    @Binds
    abstract fun bindCurrencyRepository(repository: CurrencyRepositoryImpl): CurrencyRepository

    @Singleton
    @Binds
    abstract fun bindCategoryRepository(repository: CategoryRepositoryImpl): CategoryRepository

    @Singleton
    @Binds
    abstract fun bindMonthlyTransactionRepository(repository: MonthlyTransactionRepositoryImpl): MonthlyTransactionRepository
}