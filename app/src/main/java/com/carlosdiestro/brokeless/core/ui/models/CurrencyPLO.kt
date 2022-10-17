package com.carlosdiestro.brokeless.core.ui.models

import androidx.annotation.DrawableRes

class CurrencyPLO(
    val id: Int,
    val name: String,
    val symbol: String,
    val goesFirst: Boolean,
    @DrawableRes val iconId: Int
)