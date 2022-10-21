package com.carlosdiestro.brokeless.di

import android.content.Context
import androidx.room.Room
import com.carlosdiestro.brokeless.core.framework.database.BrokelessDatabase
import com.carlosdiestro.brokeless.core.framework.database.category.CategoryDao
import com.carlosdiestro.brokeless.core.framework.database.currency.CurrencyDao
import com.carlosdiestro.brokeless.core.framework.database.montthly_transactions.MonthlyTransactionDao
import com.carlosdiestro.brokeless.core.framework.preferences.UserPreferencesService
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

    /**
     * Database
     */

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BrokelessDatabase {
        return Room
            .databaseBuilder(
                context,
                BrokelessDatabase::class.java,
                "brokeless_db"
            )
            .createFromAsset("databases/database.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyDao(db: BrokelessDatabase): CurrencyDao = db.currencyDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db: BrokelessDatabase): CategoryDao = db.categoryDao()

    @Singleton
    @Provides
    fun provideMonthlyTransactionDao(db: BrokelessDatabase): MonthlyTransactionDao =
        db.monthlyTransactionDao()
}