package com.carlosdiestro.brokeless.core.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carlosdiestro.brokeless.core.framework.database.category.CategoryDao
import com.carlosdiestro.brokeless.core.framework.database.category.CategoryEntity
import com.carlosdiestro.brokeless.core.framework.database.currency.CurrencyDao
import com.carlosdiestro.brokeless.core.framework.database.currency.CurrencyEntity
import com.carlosdiestro.brokeless.core.framework.database.montthly_transactions.MonthlyTransactionDao
import com.carlosdiestro.brokeless.core.framework.database.montthly_transactions.MonthlyTransactionEntity
import com.carlosdiestro.brokeless.core.framework.database.transaction.TransactionDao
import com.carlosdiestro.brokeless.core.framework.database.transaction.TransactionEntity

@Database(
    entities = [
        CurrencyEntity::class,
        CategoryEntity::class,
        MonthlyTransactionEntity::class,
        TransactionEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class BrokelessDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun categoryDao(): CategoryDao
    abstract fun monthlyTransactionDao(): MonthlyTransactionDao
    abstract fun transactionDao(): TransactionDao
}