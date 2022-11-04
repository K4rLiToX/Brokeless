package com.carlosdiestro.brokeless.main.statistics.ui.models

import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.utils.round

class CategoryStatisticsPLO(
    val category: CategoryPLO,
    val spent: Double,
    val received: Double,
) {
    val balance: Double
        get() = (spent + received).round(2)
}