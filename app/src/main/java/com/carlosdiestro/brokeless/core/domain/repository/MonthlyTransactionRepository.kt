package com.carlosdiestro.brokeless.core.domain.repository

import com.carlosdiestro.brokeless.core.domain.models.MonthlyTransaction
import kotlinx.coroutines.flow.Flow

interface MonthlyTransactionRepository {
    suspend fun insert(entity: MonthlyTransaction)
    suspend fun insert(entities: List<MonthlyTransaction>)
    suspend fun update(entity: MonthlyTransaction)
    suspend fun delete(entity: MonthlyTransaction)
    fun getAll(): Flow<List<MonthlyTransaction>>
    fun getIncomes(): Flow<List<MonthlyTransaction>>
    fun getExpenses(): Flow<List<MonthlyTransaction>>
}