package com.carlosdiestro.brokeless.utils

import com.carlosdiestro.brokeless.core.domain.models.Currency
import com.carlosdiestro.brokeless.core.framework.database.currency.CurrencyEntity
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Currency.toPLO(): CurrencyPLO = CurrencyPLO(
    id = id,
    name = name,
    symbol = symbol,
    iconId = ResourceManager.getDrawableResource(iconId),
    goesFirst = goesFirst
)

fun List<Currency>.toPLO(): List<CurrencyPLO> = this.map { it.toPLO() }

fun Flow<List<Currency>>.toPLO(): Flow<List<CurrencyPLO>> = this.map { it.toPLO() }

fun CurrencyEntity.toDomain(): Currency = Currency(
    id = id,
    name = name,
    symbol = symbol,
    iconId = iconId,
    goesFirst = goesFirst
)

fun List<CurrencyEntity>.toDomain(): List<Currency> = this.map { it.toDomain() }

fun Flow<List<CurrencyEntity>>.toDomain(): Flow<List<Currency>> = this.map { it.toDomain() }