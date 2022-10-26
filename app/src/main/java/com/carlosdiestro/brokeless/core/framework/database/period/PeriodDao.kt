package com.carlosdiestro.brokeless.core.framework.database.period

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PeriodDao {

    @Insert
    suspend fun insert(entity: PeriodEntity): Long

    @Update
    suspend fun update(entity: PeriodEntity)

    @Query("SELECT * FROM period_table ORDER BY startDate DESC")
    fun getAll(): Flow<List<PeriodEntity>>
}