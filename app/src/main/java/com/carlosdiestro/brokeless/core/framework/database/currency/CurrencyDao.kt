package com.carlosdiestro.brokeless.core.framework.database.currency

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert
    suspend fun insert(entity: CurrencyEntity)

    @Query("SELECT * FROM currency_table")
    fun getAll(): Flow<List<CurrencyEntity>>
}