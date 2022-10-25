package com.carlosdiestro.brokeless.main.new_transaction.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import com.carlosdiestro.brokeless.main.budget.domain.repository.TransactionRepository
import com.carlosdiestro.brokeless.main.budget.ui.models.TransactionPLO
import com.carlosdiestro.brokeless.utils.mappers.toDomain
import javax.inject.Inject

class InsertTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(transaction: TransactionPLO) {
        repository.insert(transaction.toDomain())
        userPreferencesRepository.updateCurrentBudget(transaction.quantity)
        userPreferencesRepository.updateTotalBalance(transaction.quantity)
        userPreferencesRepository.updateAvailable(transaction.quantity)
    }
}