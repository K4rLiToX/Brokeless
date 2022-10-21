package com.carlosdiestro.brokeless.core.framework.database.montthly_transactions

import androidx.room.*
import com.carlosdiestro.brokeless.core.framework.database.middle_tables.MonthlyTransactionsWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthlyTransactionDao {

    @Insert
    suspend fun insert(entity: MonthlyTransactionEntity)

    @Insert
    suspend fun insert(entities: List<MonthlyTransactionEntity>)

    @Update
    suspend fun update(entity: MonthlyTransactionEntity)

    @Delete
    suspend fun delete(entity: MonthlyTransactionEntity)

    @Query("SELECT * FROM table_monthly_transaction")
    fun getAll(): Flow<List<MonthlyTransactionsWithCategory>>

    @Query("SELECT * FROM table_monthly_transaction WHERE quantity > 0")
    fun getIncomes(): Flow<List<MonthlyTransactionsWithCategory>>

    @Query("SELECT * FROM table_monthly_transaction WHERE quantity < 0")
    fun getExpenses(): Flow<List<MonthlyTransactionsWithCategory>>
}