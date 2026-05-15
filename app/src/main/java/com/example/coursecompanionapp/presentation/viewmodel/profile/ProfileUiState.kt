package com.example.coursecompanionapp.presentation.viewmodel.profile

sealed interface ProfileUiState {
    data object Init : ProfileUiState
    data object Loading : ProfileUiState
    data class Success(
        val name: String,
        val email: String,
        val university: String,
        val department: String
    ) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}