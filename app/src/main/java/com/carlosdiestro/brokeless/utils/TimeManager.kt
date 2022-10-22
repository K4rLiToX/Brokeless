package com.carlosdiestro.brokeless.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

object TimeManager {

    fun toStringDate(date: Long): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(date))
    }

    fun toLongDate(date: String): Long {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date)!!.time
    }

}