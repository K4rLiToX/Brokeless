package com.carlosdiestro.brokeless.utils

import com.carlosdiestro.brokeless.core.domain.models.Category
import com.carlosdiestro.brokeless.core.framework.database.category.CategoryEntity
import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Category.toPLO(): CategoryPLO = CategoryPLO(
    id = id,
    textId = ResourceManager.getStringResource(textId),
    iconId = ResourceManager.getDrawableResource(iconId),
    limit = limit,
    isActive = isActive
)

fun List<Category>.toPLO(): List<CategoryPLO> = this.map { it.toPLO() }

fun Flow<List<Category>>.toPLO(): Flow<List<CategoryPLO>> = this.map { it.toPLO() }

fun CategoryEntity.toDomain(): Category = Category(
    id = id,
    textId = textId,
    iconId = iconId,
    isActive = isActive,
    limit = limit
)

fun List<CategoryEntity>.toDomain(): List<Category> = this.map { it.toDomain() }

fun Flow<List<CategoryEntity>>.toDomain(): Flow<List<Category>> = this.map { it.toDomain() }