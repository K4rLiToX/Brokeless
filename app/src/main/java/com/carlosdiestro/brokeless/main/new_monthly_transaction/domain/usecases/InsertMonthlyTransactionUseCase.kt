package com.carlosdiestro.brokeless.main.new_monthly_transaction.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.MonthlyTransactionRepository
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO
import com.carlosdiestro.brokeless.utils.mappers.toDomain
import javax.inject.Inject

class InsertMonthlyTransactionUseCase @Inject constructor(
    private val repository: MonthlyTransactionRepository
) {

    suspend operator fun invoke(monthlyTransaction: MonthlyTransactionPLO) {
        repository.insert(monthlyTransaction.toDomain())
    }
}