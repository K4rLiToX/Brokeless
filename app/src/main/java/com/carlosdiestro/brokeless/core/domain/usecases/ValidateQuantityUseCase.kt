package com.carlosdiestro.brokeless.core.domain.usecases

import javax.inject.Inject


class ValidateQuantityUseCase @Inject constructor() {

    private val pattern = Regex("^[1-9]+(.[0-9]+)?$")

    operator fun invoke(value: String, isZeroValid: Boolean = true): Boolean {
        return when {
            value.isBlank()                -> false
            value == "0.0" && isZeroValid  -> true
            value == "0.0" && !isZeroValid -> false
            else                           -> value.matches(pattern)
        }
    }
}