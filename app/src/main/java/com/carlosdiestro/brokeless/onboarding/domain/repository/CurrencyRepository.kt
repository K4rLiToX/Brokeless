package com.carlosdiestro.brokeless.onboarding.domain.repository

import com.carlosdiestro.brokeless.core.domain.models.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun getAll(): Flow<List<Currency>>
}