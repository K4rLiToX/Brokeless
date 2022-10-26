package com.carlosdiestro.brokeless.core.framework.database.period

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "period_table"
)
class PeriodEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val startDate: Long,
    val endDate: Long? = null
)