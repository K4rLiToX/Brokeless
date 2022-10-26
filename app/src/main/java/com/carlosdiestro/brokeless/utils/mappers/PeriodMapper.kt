package com.carlosdiestro.brokeless.utils.mappers

import com.carlosdiestro.brokeless.core.framework.database.period.PeriodEntity
import com.carlosdiestro.brokeless.main.transactions.domain.models.Period
import com.carlosdiestro.brokeless.main.transactions.ui.models.PeriodPLO
import com.carlosdiestro.brokeless.utils.TimeManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun PeriodEntity.toDomain(): Period = Period(
    id = id!!,
    startDate = startDate,
    endDate = endDate
)

fun List<PeriodEntity>.toDomain(): List<Period> = this.map { it.toDomain() }

fun Flow<List<PeriodEntity>>.toDomain(): Flow<List<Period>> = this.map { it.toDomain() }

fun Period.toEntity(): PeriodEntity = PeriodEntity(
    id = if (id == -1) null else id,
    startDate = startDate,
    endDate = endDate
)

fun Period.toPLO(): PeriodPLO = PeriodPLO(
    id = id,
    startDate = TimeManager.toStringDate(startDate, "d MMM"),
    endDate = endDate?.let { TimeManager.toStringDate(it, "d MMM") } ?: ""
)

fun List<Period>.toPLO(): List<PeriodPLO> = this.map { it.toPLO() }

fun Flow<List<Period>>.toPLO(): Flow<List<PeriodPLO>> = this.map { it.toPLO() }

fun PeriodPLO.toDomain(): Period = Period(
    id = id,
    startDate = TimeManager.toLongDate(startDate, "d MMMM"),
    endDate = if (endDate != "") TimeManager.toLongDate(endDate, "d MMM") else null
)