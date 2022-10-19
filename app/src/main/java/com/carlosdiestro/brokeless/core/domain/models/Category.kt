package com.carlosdiestro.brokeless.core.domain.models

class Category(
    val id: Int,
    val textId: String,
    val iconId: String,
    val limit: Double?,
    val isActive: Boolean
)