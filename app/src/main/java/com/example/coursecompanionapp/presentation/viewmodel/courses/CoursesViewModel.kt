package com.example.coursecompanionapp.presentation.viewmodel.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursecompanionapp.model.Course
import com.example.coursecompanionapp.model.repository.CoursesRepository
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
    private val repository: CoursesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CoursesUiState>(CoursesUiState.Init)
    val uiState: StateFlow<CoursesUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<CoursesNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            _uiState.value = CoursesUiState.Loading
            try {
                _courses.value = repository.getCourses()
                _uiState.value = CoursesUiState.Success(_courses.value)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = CoursesUiState.Error(e.message ?: "Failed to load courses.")
            }
        }
    }

    fun addCourse(course: Course) {
        viewModelScope.launch {
            _uiState.value = CoursesUiState.Loading
            try {
                _courses.value = repository.addCourse(course, _courses.value)
                _uiState.value = CoursesUiState.Success(_courses.value)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = CoursesUiState.Error(e.message ?: "Failed to add course.")
            }
        }
    }

    fun resetUiState() {
        loadCourses()
    }
}