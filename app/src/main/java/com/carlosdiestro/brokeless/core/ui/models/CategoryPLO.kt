package com.carlosdiestro.brokeless.core.ui.models

import androidx.annotation.DrawableRes

class CategoryPLO(
    val id: Int,
    val name: String,
    @DrawableRes val iconId: Int,
    val isActive: Boolean,
    val limit: Double?,
)