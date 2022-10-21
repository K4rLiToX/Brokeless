package com.carlosdiestro.brokeless.core.data.repository

import com.carlosdiestro.brokeless.core.domain.models.Currency
import com.carlosdiestro.brokeless.core.framework.database.currency.CurrencyDao
import com.carlosdiestro.brokeless.onboarding.domain.repository.CurrencyRepository
import com.carlosdiestro.brokeless.utils.mappers.toDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val dao: CurrencyDao
) : CurrencyRepository {

    override fun getAll(): Flow<List<Currency>> = dao.getAll().toDomain()
}