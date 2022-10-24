package com.carlosdiestro.brokeless.onboarding.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.MonthlyTransactionRepository
import com.carlosdiestro.brokeless.core.domain.repository.UserPreferencesRepository
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO
import com.carlosdiestro.brokeless.utils.mappers.toDomain
import javax.inject.Inject

class SetUpUseCases @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val monthlyTransactionsRepository: MonthlyTransactionRepository
) {

    inner class UpdateCurrencyUseCase {
        suspend operator fun invoke(currency: CurrencyPLO) {
            userPreferencesRepository.updateCurrency(currency.toDomain())
        }
    }

    inner class UpdateTotalBalanceUseCase {
        suspend operator fun invoke(totalBalance: Double) {
            userPreferencesRepository.updateTotalBalance(totalBalance)
        }
    }

    inner class UpdateMonthlyTransactionsUseCase {
        suspend operator fun invoke(monthlyTransactions: List<MonthlyTransactionPLO>) {
            monthlyTransactionsRepository.insert(monthlyTransactions.toDomain())
        }
    }

    inner class UpdateSavingsUseCase {
        suspend operator fun invoke(savings: Double) {
            userPreferencesRepository.updateSavings(savings)
        }
    }

    inner class UpdateSavingsPercentageUseCase {
        suspend operator fun invoke(value: Double) {
            userPreferencesRepository.updateSavingsPercentage(value)
        }
    }

    inner class UpdateFirstTimeUseCase {
        suspend operator fun invoke() = userPreferencesRepository.updateFirstTime()
    }
}