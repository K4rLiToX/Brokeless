package com.carlosdiestro.brokeless.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeManager {

    fun toStringDate(date: Long, format: String = "dd/MM/yyyy"): String {
        return SimpleDateFormat(format, Locale.getDefault()).format(Date(date))
    }

    fun toLongDate(date: String, format: String = "dd/MM/yyyy"): Long {
        return SimpleDateFormat(format, Locale.getDefault()).parse(date)!!.time
    }

    fun nowString(format: String = "dd/MM/yyyy"): String {
        return SimpleDateFormat(
            format,
            Locale.getDefault()
        ).format(Date(System.currentTimeMillis()))
    }

    fun nowLong(): Long {
        return System.currentTimeMillis()
    }

}