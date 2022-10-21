package com.carlosdiestro.brokeless.core.data.repository

import com.carlosdiestro.brokeless.core.domain.models.MonthlyTransaction
import com.carlosdiestro.brokeless.core.domain.repository.MonthlyTransactionRepository
import com.carlosdiestro.brokeless.core.framework.database.montthly_transactions.MonthlyTransactionDao
import com.carlosdiestro.brokeless.utils.mappers.toDomain
import com.carlosdiestro.brokeless.utils.mappers.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MonthlyTransactionRepositoryImpl @Inject constructor(
    private val dao: MonthlyTransactionDao
) : MonthlyTransactionRepository {

    override suspend fun insert(entity: MonthlyTransaction) = dao.insert(entity.toEntity())

    override suspend fun insert(entities: List<MonthlyTransaction>) =
        dao.insert(entities.toEntity())

    override suspend fun update(entity: MonthlyTransaction) = dao.update(entity.toEntity())

    override suspend fun delete(entity: MonthlyTransaction) = dao.delete(entity.toEntity())

    override fun getAll(): Flow<List<MonthlyTransaction>> = dao.getAll().toDomain()

    override fun getIncomes(): Flow<List<MonthlyTransaction>> = dao.getIncomes().toDomain()

    override fun getExpenses(): Flow<List<MonthlyTransaction>> = dao.getExpenses().toDomain()
}