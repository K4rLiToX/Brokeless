package com.carlosdiestro.brokeless.statistics.ui.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class CategoryStatisticsPLO(
    val id: Int,
    @StringRes val textId: Int,
    @DrawableRes val iconId: Int,
    val limit: Double?,
    val spent: Double,
    val received: Double,
) {
    val balance: Double
        get() = -(spent + received)
}