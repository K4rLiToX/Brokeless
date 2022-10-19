package com.carlosdiestro.brokeless.utils

import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO

fun List<MonthlyTransactionPLO>.incomes() = this.filter { it.quantity > 0.0 }
fun List<MonthlyTransactionPLO>.expenses() = this.filter { it.quantity < 0.0 }

fun List<MonthlyTransactionPLO>.total() = this.fold(0.0) { acc, m -> acc + m.quantity }