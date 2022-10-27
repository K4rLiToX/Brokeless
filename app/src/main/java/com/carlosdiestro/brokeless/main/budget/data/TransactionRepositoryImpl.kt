package com.carlosdiestro.brokeless.main.budget.data

import com.carlosdiestro.brokeless.core.framework.database.transaction.TransactionDao
import com.carlosdiestro.brokeless.main.budget.domain.Transaction
import com.carlosdiestro.brokeless.main.budget.domain.repository.TransactionRepository
import com.carlosdiestro.brokeless.utils.mappers.toDomain
import com.carlosdiestro.brokeless.utils.mappers.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {

    override fun getAll(): Flow<List<Transaction>> = dao.getAll().toDomain()

    override fun getRecent(): Flow<List<Transaction>> = dao.getRecent().toDomain()

    override suspend fun insert(transaction: Transaction) = dao.insert(transaction.toEntity())

    override suspend fun insert(transactions: List<Transaction>) = dao.insert(transactions.toEntity())
}