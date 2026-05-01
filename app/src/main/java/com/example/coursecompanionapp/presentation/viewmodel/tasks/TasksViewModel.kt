package com.example.coursecompanionapp.presentation.viewmodel.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursecompanionapp.model.Task
import com.example.coursecompanionapp.model.repository.TasksRepository
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
class TasksViewModel @Inject constructor(
    private val repository: TasksRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TasksUiState>(TasksUiState.Init)
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<TasksNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = TasksUiState.Loading
            try {
                _tasks.value = repository.getTasks()
                _uiState.value = TasksUiState.Success(_tasks.value)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = TasksUiState.Error(e.message ?: "Failed to load tasks.")
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            _uiState.value = TasksUiState.Loading
            try {
                _tasks.value = repository.addTask(task, _tasks.value)
                _uiState.value = TasksUiState.Success(_tasks.value)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = TasksUiState.Error(e.message ?: "Failed to add task.")
            }
        }
    }

    fun toggleTask(taskId: Int) {
        viewModelScope.launch {
            _uiState.value = TasksUiState.Loading
            try {
                _tasks.value = repository.toggleTask(taskId, _tasks.value)
                _uiState.value = TasksUiState.Success(_tasks.value)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = TasksUiState.Error(e.message ?: "Failed to toggle task.")
            }
        }
    }

    fun resetUiState() {
        loadTasks()
    }
}