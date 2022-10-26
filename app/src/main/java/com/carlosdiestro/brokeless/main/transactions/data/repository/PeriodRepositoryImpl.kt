package com.carlosdiestro.brokeless.main.transactions.data.repository

import com.carlosdiestro.brokeless.core.framework.database.period.PeriodDao
import com.carlosdiestro.brokeless.main.transactions.domain.models.Period
import com.carlosdiestro.brokeless.main.transactions.domain.repository.PeriodRepository
import com.carlosdiestro.brokeless.utils.mappers.toDomain
import com.carlosdiestro.brokeless.utils.mappers.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PeriodRepositoryImpl @Inject constructor(
    private val dao: PeriodDao
) : PeriodRepository {

    override suspend fun insert(period: Period): Long = dao.insert(period.toEntity())

    override suspend fun update(period: Period) = dao.update(period.toEntity())

    override fun getAll(): Flow<List<Period>> = dao.getAll().toDomain()
}