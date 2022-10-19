package com.carlosdiestro.brokeless.core.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carlosdiestro.brokeless.core.framework.database.category.CategoryDao
import com.carlosdiestro.brokeless.core.framework.database.category.CategoryEntity
import com.carlosdiestro.brokeless.core.framework.database.currency.CurrencyDao
import com.carlosdiestro.brokeless.core.framework.database.currency.CurrencyEntity

@Database(
    entities = [
        CurrencyEntity::class,
        CategoryEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class BrokelessDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun categoryDao(): CategoryDao
}