package com.carlosdiestro.brokeless.main.wallet.domain.usecases

import com.carlosdiestro.brokeless.core.domain.repository.CategoryRepository
import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.utils.mappers.toPLO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    operator fun invoke(id: Int): Flow<CategoryPLO> = repository.getById(id).toPLO()
}