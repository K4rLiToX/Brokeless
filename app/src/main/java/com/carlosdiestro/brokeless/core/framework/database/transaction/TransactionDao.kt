package com.carlosdiestro.brokeless.core.framework.database.transaction

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.carlosdiestro.brokeless.core.framework.database.middle_tables.TransactionWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(entity: TransactionEntity)

    @Insert
    suspend fun insert(entities: List<TransactionEntity>)

    @Transaction
    @Query("SELECT * FROM transaction_table ORDER BY id DESC")
    fun getAll(): Flow<List<TransactionWithCategory>>

    @Transaction
    @Query("SELECT * FROM transaction_table ORDER BY id DESC LIMIT 3")
    fun getRecent(): Flow<List<TransactionWithCategory>>
}