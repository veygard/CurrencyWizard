package com.veygard.currencywizzard.presentation.screens.start

sealed class StartScreenState {
    object ConnectionError: StartScreenState()
    object Loading: StartScreenState()
    object Success: StartScreenState()
}