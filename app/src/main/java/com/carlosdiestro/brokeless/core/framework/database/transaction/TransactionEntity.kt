package com.carlosdiestro.brokeless.core.framework.database.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "transaction_table"
)
class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val concept: String,
    val description: String,
    val quantity: Double,
    val categoryId: Int,
    val date: Long
)