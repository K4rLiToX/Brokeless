package com.carlosdiestro.brokeless.main.transactions.domain.models

data class Period(
    val id: Int,
    val startDate: Long,
    val endDate: Long?
)