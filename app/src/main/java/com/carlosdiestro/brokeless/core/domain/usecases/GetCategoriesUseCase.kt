package com.carlosdiestro.brokeless.core.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.CategoryRepository
import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.utils.mappers.toPLO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    operator fun invoke(): Flow<List<CategoryPLO>> = repository.getAll().toPLO()
}