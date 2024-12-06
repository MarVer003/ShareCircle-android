package com.example.sharecircle.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sharecircle.ui.screens.ExpenseViewScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShareCircleViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseViewScreenUIState())
    val uiState: StateFlow<ExpenseViewScreenUIState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        _uiState.value = ExpenseViewScreenUIState()
    }
}