package com.carlosdiestro.brokeless.core.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigationManager {

    private var _commands: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)
    val commands = _commands.asStateFlow()

    fun navigate(
        directions: NavigationCommand
    ) {
        _commands.update { directions }
    }
}