package com.carlosdiestro.brokeless.core.domain.repository

import com.carlosdiestro.brokeless.core.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAll(): Flow<List<Category>>
}