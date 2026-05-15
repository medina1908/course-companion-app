package com.example.coursecompanionapp.presentation.viewmodel.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursecompanionapp.model.data.local.entity.CourseEntity
import com.example.coursecompanionapp.model.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(
    private val repository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CoursesUiState>(CoursesUiState.Init)
    val uiState: StateFlow<CoursesUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<CoursesNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _courses = MutableStateFlow<List<CourseEntity>>(emptyList())
    val courses: StateFlow<List<CourseEntity>> = _courses.asStateFlow()

    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            _uiState.value = CoursesUiState.Loading
            try {
                repository.getAllCourses().collect { courses ->
                    _courses.value = courses
                    _uiState.value = CoursesUiState.Success(courses)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = CoursesUiState.Error(e.message ?: "Failed to load courses.")
            }
        }
    }

    fun addCourse(course: CourseEntity) {
        viewModelScope.launch {
            try {
                repository.insertCourse(course)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = CoursesUiState.Error(e.message ?: "Failed to add course.")
            }
        }
    }
    fun deleteCourse(course: CourseEntity) {
        viewModelScope.launch {
            try {
                repository.deleteCourse(course)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = CoursesUiState.Error(e.message ?: "Failed to delete course.")
            }
        }
    }

    fun updateCourse(course: CourseEntity) {
        viewModelScope.launch {
            try {
                repository.updateCourse(course)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = CoursesUiState.Error(e.message ?: "Failed to update course.")
            }
        }
    }

    fun resetUiState() {
        loadCourses()
    }

}