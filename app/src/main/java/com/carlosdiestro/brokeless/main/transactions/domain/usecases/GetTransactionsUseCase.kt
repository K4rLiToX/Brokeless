package com.carlosdiestro.brokeless.main.transactions.domain.usecases

import com.carlosdiestro.brokeless.main.budget.domain.repository.TransactionRepository
import com.carlosdiestro.brokeless.main.budget.ui.models.TransactionPLO
import com.carlosdiestro.brokeless.utils.mappers.toPLO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
){

    operator fun invoke(): Flow<List<TransactionPLO>> = repository.getAll().toPLO()
}