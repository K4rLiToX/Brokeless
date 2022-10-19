package com.carlosdiestro.brokeless.core.data.repository

import com.carlosdiestro.brokeless.core.domain.models.Category
import com.carlosdiestro.brokeless.core.domain.repository.CategoryRepository
import com.carlosdiestro.brokeless.core.framework.database.category.CategoryDao
import com.carlosdiestro.brokeless.utils.toDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dao: CategoryDao
) : CategoryRepository {

    override fun getAll(): Flow<List<Category>> = dao.getAll().toDomain()
}