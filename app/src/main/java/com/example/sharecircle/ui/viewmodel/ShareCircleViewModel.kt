package com.example.sharecircle.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sharecircle.ui.screens.HomeScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShareCircleViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUIState())
    val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()

    init {
        resetExpensesAndBackPayements()
    }

    private fun resetExpensesAndBackPayements() {
        _uiState.value = HomeScreenUIState()
    }
}