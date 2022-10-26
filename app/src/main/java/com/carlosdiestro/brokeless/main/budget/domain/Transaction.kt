package com.carlosdiestro.brokeless.main.budget.domain

import com.carlosdiestro.brokeless.core.domain.models.Category

class Transaction(
    val id: Int,
    val concept: String,
    val description: String,
    val quantity: Double,
    val category: Category,
    val date: String
)