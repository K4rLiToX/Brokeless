package com.carlosdiestro.brokeless.utils.mappers

import com.carlosdiestro.brokeless.core.framework.database.middle_tables.TransactionWithCategory
import com.carlosdiestro.brokeless.core.framework.database.transaction.TransactionEntity
import com.carlosdiestro.brokeless.main.budget.domain.Transaction
import com.carlosdiestro.brokeless.main.budget.ui.models.TransactionPLO
import com.carlosdiestro.brokeless.utils.TimeManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun TransactionWithCategory.toDomain(): Transaction = Transaction(
    id = transaction.id!!,
    concept = transaction.concept,
    description = transaction.description,
    quantity = transaction.quantity,
    category = category.toDomain(),
    date = TimeManager.toStringDate(transaction.date)
)

fun List<TransactionWithCategory>.toDomain(): List<Transaction> = this.map { it.toDomain() }

fun Flow<List<TransactionWithCategory>>.toDomain(): Flow<List<Transaction>> =
    this.map { it.toDomain() }

fun Transaction.toPLO(): TransactionPLO = TransactionPLO(
    id = id,
    concept = concept,
    description = description,
    quantity = quantity,
    category = category.toPLO(),
    date = date
)

fun List<Transaction>.toPLO(): List<TransactionPLO> = this.map { it.toPLO() }

fun Flow<List<Transaction>>.toPLO(): Flow<List<TransactionPLO>> = this.map { it.toPLO() }

fun TransactionPLO.toDomain(): Transaction = Transaction(
    id = id,
    concept = concept,
    description = description,
    quantity = quantity,
    category = category.toDomain(),
    date = date
)

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id = if (id != -1) id else null,
    concept = concept,
    description = description,
    quantity = quantity,
    categoryId = category.id,
    date = TimeManager.toLongDate(date)
)