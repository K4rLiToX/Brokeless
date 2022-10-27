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

    @Query("SELECT * FROM transaction_table ORDER BY id DESC")
    fun getAll(): Flow<List<TransactionWithCategory>>

    @Query("SELECT * FROM transaction_table ORDER BY id DESC LIMIT 3")
    fun getRecent(): Flow<List<TransactionWithCategory>>
}