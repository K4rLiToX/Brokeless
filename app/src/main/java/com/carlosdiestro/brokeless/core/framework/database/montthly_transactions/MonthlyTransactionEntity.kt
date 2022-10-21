package com.carlosdiestro.brokeless.core.framework.database.montthly_transactions

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_monthly_transaction"
)
class MonthlyTransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val concept: String,
    val quantity: Double,
    val categoryId: Int,
    val isActive: Boolean = true,
)