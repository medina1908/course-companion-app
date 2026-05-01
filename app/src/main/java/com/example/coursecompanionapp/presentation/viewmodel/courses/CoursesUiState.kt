package com.example.coursecompanionapp.presentation.viewmodel.courses

import com.example.coursecompanionapp.model.Course

sealed interface CoursesUiState {
    data object Init : CoursesUiState
    data object Loading : CoursesUiState
    data class Success(val courses: List<Course>) : CoursesUiState
    data class Error(val message: String) : CoursesUiState
}