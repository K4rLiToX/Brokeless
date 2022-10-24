package com.carlosdiestro.brokeless.main.new_transaction.domain.usecases

import com.carlosdiestro.brokeless.main.budget.domain.repository.TransactionRepository
import com.carlosdiestro.brokeless.main.budget.ui.models.TransactionPLO
import com.carlosdiestro.brokeless.utils.mappers.toDomain
import javax.inject.Inject

class InsertTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transaction: TransactionPLO) {
        repository.insert(transaction.toDomain())
    }
}