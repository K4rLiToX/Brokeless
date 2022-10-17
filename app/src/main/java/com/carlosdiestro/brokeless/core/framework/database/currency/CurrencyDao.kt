package com.carlosdiestro.brokeless.core.framework.database.currency

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency_tables")
    fun getAll(): Flow<List<CurrencyEntity>>
}