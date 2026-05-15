package com.example.coursecompanionapp.presentation.viewmodel.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursecompanionapp.model.data.local.entity.CourseEntity
import com.example.coursecompanionapp.model.data.local.entity.TaskEntity
import com.example.coursecompanionapp.model.repository.CourseRepository
import com.example.coursecompanionapp.model.repository.TaskRepository
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
class DashboardViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Init)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<DashboardNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _courses = MutableStateFlow<List<CourseEntity>>(emptyList())
    val courses: StateFlow<List<CourseEntity>> = _courses.asStateFlow()

    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks.asStateFlow()

    init {
        loadDashboard()
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading
            try {
                courseRepository.getAllCourses().collect { courses ->
                    _courses.value = courses
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error(e.message ?: "Failed to load dashboard.")
            }
        }
        viewModelScope.launch {
            try {
                taskRepository.getAllTasks().collect { tasks ->
                    _tasks.value = tasks
                    _uiState.value = DashboardUiState.Success
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error(e.message ?: "Failed to load tasks.")
            }
        }
    }

    fun resetUiState() {
        loadDashboard()
    }
}