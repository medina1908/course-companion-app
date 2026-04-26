package com.example.coursecompanionapp.presentation.viewmodel.courses

import com.example.coursecompanionapp.model.Course

data class CoursesUiState(
    val courses: List<Course> = emptyList(),
    val showForm: Boolean = false,
    val courseName: String = "",
    val professor: String = "",
    val credits: String = "",
    val searchQuery: String = ""
)