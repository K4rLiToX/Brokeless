package com.carlosdiestro.brokeless.utils

import java.math.BigDecimal
import java.math.RoundingMode

infix fun Double.directProportion(total: Double): Double = (this / total).round(2)

fun Double.asPercentage(): Double = (this * 100).round(2)

fun Double.asDirectProportion(): Double = (this / 100).round(2)

fun Double.round(numberOfDecimals: Int = 2): Double {
    return BigDecimal(this).setScale(numberOfDecimals, RoundingMode.HALF_EVEN).toDouble()
}