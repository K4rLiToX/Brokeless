package com.carlosdiestro.brokeless.core.domain.usecases

import javax.inject.Inject

class ValidateTextUseCase @Inject constructor() {

    operator fun invoke(value: String): Boolean {
        return value.isNotBlank()
    }
}