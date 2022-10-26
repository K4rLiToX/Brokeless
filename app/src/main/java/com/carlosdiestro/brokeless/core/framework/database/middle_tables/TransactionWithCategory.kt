package com.carlosdiestro.brokeless.core.framework.database.middle_tables

import androidx.room.Embedded
import androidx.room.Relation
import com.carlosdiestro.brokeless.core.framework.database.category.CategoryEntity
import com.carlosdiestro.brokeless.core.framework.database.transaction.TransactionEntity

class TransactionWithCategory(
    @Embedded
    val transaction: TransactionEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity
)