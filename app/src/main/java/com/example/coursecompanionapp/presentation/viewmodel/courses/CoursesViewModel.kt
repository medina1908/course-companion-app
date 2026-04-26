package com.example.coursecompanionapp.presentation.viewmodel.courses

import androidx.lifecycle.ViewModel
import com.example.coursecompanionapp.model.Course
import com.example.coursecompanionapp.model.HardcodedData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CoursesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CoursesUiState())
    val uiState: StateFlow<CoursesUiState> = _uiState

    init {
        loadCourses()
    }

    private fun loadCourses() {
        _uiState.update {
            it.copy(courses = HardcodedData.courses)
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update {
            it.copy(searchQuery = query)
        }
    }

    fun onToggleForm() {
        _uiState.update {
            it.copy(showForm = !it.showForm)
        }
    }

    fun onCourseNameChange(value: String) {
        _uiState.update { it.copy(courseName = value) }
    }

    fun onProfessorChange(value: String) {
        _uiState.update { it.copy(professor = value) }
    }

    fun onCreditsChange(value: String) {
        if (value.all { it.isDigit() }) {
            _uiState.update { it.copy(credits = value) }
        }
    }

    fun onSaveCourse() {
        val state = _uiState.value

        if (state.courseName.isNotBlank()
            && state.professor.isNotBlank()
            && state.credits.isNotBlank()
        ) {
            val newCourse = Course(
                id = state.courses.size + 1,
                name = state.courseName,
                professor = state.professor,
                credits = state.credits.toInt()
            )

            _uiState.update {
                it.copy(
                    courses = it.courses + newCourse,
                    courseName = "",
                    professor = "",
                    credits = "",
                    showForm = false
                )
            }
        }
    }
}