package com.carlosdiestro.brokeless.utils.mappers

import com.carlosdiestro.brokeless.core.domain.models.MonthlyTransaction
import com.carlosdiestro.brokeless.core.framework.database.middle_tables.MonthlyTransactionsWithCategory
import com.carlosdiestro.brokeless.core.framework.database.montthly_transactions.MonthlyTransactionEntity
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun MonthlyTransactionsWithCategory.toDomain(): MonthlyTransaction = MonthlyTransaction(
    id = monthlyTransaction.id!!,
    concept = monthlyTransaction.concept,
    quantity = monthlyTransaction.quantity,
    category = category.toDomain(),
    isActive = monthlyTransaction.isActive
)

fun List<MonthlyTransactionsWithCategory>.toDomain(): List<MonthlyTransaction> = this.map { it.toDomain() }

fun Flow<List<MonthlyTransactionsWithCategory>>.toDomain(): Flow<List<MonthlyTransaction>> = this.map { it.toDomain() }

fun MonthlyTransaction.toEntity(): MonthlyTransactionEntity = MonthlyTransactionEntity(
    id = if(id == -1) null else id,
    concept = concept,
    quantity = quantity,
    categoryId = category.id,
    isActive = isActive
)

fun List<MonthlyTransaction>.toEntity(): List<MonthlyTransactionEntity> = this.map { it.toEntity() }

fun MonthlyTransaction.toPLO(): MonthlyTransactionPLO = MonthlyTransactionPLO(
    id = id,
    concept = concept,
    quantity = quantity,
    category = category.toPLO(),
    isActive = isActive
)

fun List<MonthlyTransaction>.toPLO(): List<MonthlyTransactionPLO> = this.map { it.toPLO() }

fun Flow<List<MonthlyTransaction>>.toPLO(): Flow<List<MonthlyTransactionPLO>> = this.map { it.toPLO() }

fun MonthlyTransactionPLO.toDomain(): MonthlyTransaction = MonthlyTransaction(
    id = id,
    concept = concept,
    quantity = quantity,
    category = category.toDomain(),
    isActive = isActive
)

@JvmName("toDomainMonthlyTransactionPLO")
fun List<MonthlyTransactionPLO>.toDomain(): List<MonthlyTransaction> = this.map { it.toDomain() }