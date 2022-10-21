package com.carlosdiestro.brokeless.core.domain.models

class MonthlyTransaction(
    val id: Int,
    val concept: String,
    val quantity: Double,
    val category: Category,
    val isActive: Boolean
)