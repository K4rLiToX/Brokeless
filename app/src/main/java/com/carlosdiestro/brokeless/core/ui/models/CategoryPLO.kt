package com.carlosdiestro.brokeless.core.ui.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class CategoryPLO(
    val id: Int,
    @StringRes val textId: Int,
    @DrawableRes val iconId: Int,
    val isActive: Boolean,
    val limit: Double?
)