package com.carlosdiestro.brokeless.main.transactions.domain.repository

import com.carlosdiestro.brokeless.main.transactions.domain.models.Period
import kotlinx.coroutines.flow.Flow

interface PeriodRepository {

    suspend fun insert(period: Period): Long
    suspend fun update(period: Period)
    fun getAll(): Flow<List<Period>>
}