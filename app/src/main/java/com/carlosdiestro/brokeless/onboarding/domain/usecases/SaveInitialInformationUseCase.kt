package com.carlosdiestro.brokeless.onboarding.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.MonthlyTransactionRepository
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO
import com.carlosdiestro.brokeless.utils.toDomain
import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SaveInitialInformationUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val monthlyTransactionsRepository: MonthlyTransactionRepository
){

    suspend operator fun invoke(
        totalBalance: Double,
        currency: CurrencyPLO,
        monthlyTransactions: List<MonthlyTransactionPLO>,
        savingsPercentage: Double
    ) {
        monthlyTransactionsRepository.insert(monthlyTransactions.toDomain())
        with(userPreferencesRepository) {
            updateTotalBalance(totalBalance)
            updateSavings(totalBalance)
            updateCurrency(currency.toDomain())
            updateSavingsPercentage(savingsPercentage)
        }
    }
}