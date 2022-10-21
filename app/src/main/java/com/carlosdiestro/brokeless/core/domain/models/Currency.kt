package com.carlosdiestro.brokeless.core.domain.models

class Currency(
    val id: Int,
    val name: String,
    val symbol: String,
    val goesFirst: Boolean,
    val iconId: String
)