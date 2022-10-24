package com.carlosdiestro.brokeless.main.budget.data

import com.carlosdiestro.brokeless.core.framework.database.transaction.TransactionDao
import com.carlosdiestro.brokeless.main.budget.domain.Transaction
import com.carlosdiestro.brokeless.main.budget.domain.repository.TransactionRepository
import com.carlosdiestro.brokeless.utils.TimeManager
import com.carlosdiestro.brokeless.utils.mappers.toDomain
import com.carlosdiestro.brokeless.utils.mappers.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {

    override fun getByPeriod(date: String): Flow<List<Transaction>> =
        dao.getByDate(TimeManager.toLongDate(date)).toDomain()

    override suspend fun insert(transaction: Transaction) = dao.insert(transaction.toEntity())
}