package com.carlosdiestro.brokeless.main.wallet.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.MonthlyTransactionRepository
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO
import com.carlosdiestro.brokeless.utils.mappers.toPLO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMonthlyTransactionsUseCase @Inject constructor(
    private val repository: MonthlyTransactionRepository
) {

    operator fun invoke(): Flow<List<MonthlyTransactionPLO>> = repository.getAll().toPLO()
}