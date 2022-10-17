package com.carlosdiestro.brokeless.core.ui.models

import androidx.annotation.DrawableRes

class CurrencyPLO(
    val symbol: String,
    val name: String,
    val goesFirst: Boolean,
    @DrawableRes val iconId: Int
)