package com.carlosdiestro.brokeless.main.budget.domain.repository

import com.carlosdiestro.brokeless.main.budget.domain.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun getAll(): Flow<List<Transaction>>
    fun getRecent(): Flow<List<Transaction>>
    suspend fun insert(transaction: Transaction)
    suspend fun insert(transactions: List<Transaction>)
}