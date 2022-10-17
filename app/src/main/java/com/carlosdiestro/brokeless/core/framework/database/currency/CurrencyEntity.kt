package com.carlosdiestro.brokeless.core.framework.database.currency

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "currency_tables"
)
class CurrencyEntity(
    @PrimaryKey
    val int: Int,
    val name: String,
    val symbol: String,
    val iconId: String,
    val goesFirst: Boolean
)