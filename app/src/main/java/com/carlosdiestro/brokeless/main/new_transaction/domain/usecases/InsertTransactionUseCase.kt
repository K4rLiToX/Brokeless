package com.carlosdiestro.brokeless.main.new_transaction.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import com.carlosdiestro.brokeless.di.ApplicationScope
import com.carlosdiestro.brokeless.main.budget.domain.repository.TransactionRepository
import com.carlosdiestro.brokeless.main.budget.ui.models.TransactionPLO
import com.carlosdiestro.brokeless.utils.mappers.toDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class InsertTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    @ApplicationScope private val externalScope: CoroutineScope
) {

    suspend operator fun invoke(transaction: TransactionPLO) {
        externalScope.launch {
            repository.insert(transaction.toDomain())
            userPreferencesRepository.updateCurrentBudget(transaction.quantity)
            userPreferencesRepository.updateTotalBalance(transaction.quantity)
            userPreferencesRepository.updateAvailable(transaction.quantity)
        }.join()
    }
}