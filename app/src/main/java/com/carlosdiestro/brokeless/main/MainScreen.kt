package com.carlosdiestro.brokeless.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.carlosdiestro.brokeless.core.navigation.MainNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {
            NavigationBar {

            }
        }
    ) { paddingValues ->
        MainNavGraph(
            modifier = Modifier.padding(paddingValues),
            navController = navController
        )
    }
}