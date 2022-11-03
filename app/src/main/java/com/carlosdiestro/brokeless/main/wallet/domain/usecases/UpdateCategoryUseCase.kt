package com.carlosdiestro.brokeless.main.wallet.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.CategoryRepository
import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.utils.mappers.toDomain
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke(category: CategoryPLO) = repository.update(category.toDomain())
}