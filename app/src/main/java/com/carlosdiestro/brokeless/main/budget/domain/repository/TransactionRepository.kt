package com.carlosdiestro.brokeless.main.budget.domain.repository

import com.carlosdiestro.brokeless.main.budget.domain.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun getByPeriod(date: String): Flow<List<Transaction>>
}