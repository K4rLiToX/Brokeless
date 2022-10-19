package com.carlosdiestro.brokeless.core.framework.database.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "category_table"
)
class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val textId: String,
    val iconId: String,
    val isActive: Boolean,
    val limit: Double?,
)