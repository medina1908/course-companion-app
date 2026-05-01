package com.example.coursecompanionapp.presentation.viewmodel.dashboard

sealed interface DashboardUiState {
    data object Init : DashboardUiState
    data object Loading : DashboardUiState
    data object Success : DashboardUiState
    data class Error(val message: String) : DashboardUiState
}