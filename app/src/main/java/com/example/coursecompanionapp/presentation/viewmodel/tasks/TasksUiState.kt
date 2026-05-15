package com.example.coursecompanionapp.presentation.viewmodel.tasks

import com.example.coursecompanionapp.model.data.local.entity.TaskEntity

sealed interface TasksUiState {
    data object Init : TasksUiState
    data object Loading : TasksUiState
    data class Success(val tasks: List<TaskEntity>) : TasksUiState
    data class Error(val message: String) : TasksUiState
}