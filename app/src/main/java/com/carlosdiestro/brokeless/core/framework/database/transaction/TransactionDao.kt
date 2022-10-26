package com.carlosdiestro.brokeless.core.framework.database.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.carlosdiestro.brokeless.core.framework.database.middle_tables.TransactionWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(entity: TransactionEntity)

    @Insert
    suspend fun insert(entities: List<TransactionEntity>)

    @Query("SELECT * FROM transaction_table WHERE date >= :date ORDER BY id DESC")
    fun getByDate(date: Long): Flow<List<TransactionWithCategory>>
}