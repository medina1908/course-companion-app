package com.example.coursecompanionapp.presentation.viewmodel.tasks

import com.example.coursecompanionapp.model.Task

sealed interface TasksUiState {
    data object Init : TasksUiState
    data object Loading : TasksUiState
    data class Success(val tasks: List<Task>) : TasksUiState
    data class Error(val message: String) : TasksUiState
}