package com.example.coursecompanionapp.presentation.viewmodel.login

sealed interface LoginUiState {
    data object Init : LoginUiState
    data object Loading : LoginUiState
    data class Success(val isLoggedIn: Boolean) : LoginUiState
    data class Error(val message: String) : LoginUiState
}