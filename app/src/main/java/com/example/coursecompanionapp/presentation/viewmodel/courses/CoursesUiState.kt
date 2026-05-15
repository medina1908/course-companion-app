package com.example.coursecompanionapp.presentation.viewmodel.courses

import com.example.coursecompanionapp.model.data.local.entity.CourseEntity

sealed interface CoursesUiState {
    data object Init : CoursesUiState
    data object Loading : CoursesUiState
    data class Success(val courses: List<CourseEntity>) : CoursesUiState
    data class Error(val message: String) : CoursesUiState
}