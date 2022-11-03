package com.carlosdiestro.brokeless.statistics.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val currency = state.value.currency
    val categories = state.value.categories
    val periodExpenses = state.value.periodExpenses
    val periodIncomes = state.value.periodIncomes
    val period = state.value.period
}