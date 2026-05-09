package com.example.coursecompanionapp.presentation.viewmodel.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursecompanionapp.model.data.local.entity.TaskEntity
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
class TasksViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TasksUiState>(TasksUiState.Init)
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<TasksNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = TasksUiState.Loading
            try {
                repository.getAllTasks().collect { tasks ->
                    _tasks.value = tasks
                    _uiState.value = TasksUiState.Success(tasks)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = TasksUiState.Error(e.message ?: "Failed to load tasks.")
            }
        }
    }

    fun addTask(task: TaskEntity) {
        viewModelScope.launch {
            try {
                repository.insertTask(task)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = TasksUiState.Error(e.message ?: "Failed to add task.")
            }
        }
    }

    fun toggleTask(taskId: Int) {
        viewModelScope.launch {
            try {
                val task = repository.getTaskById(taskId)
                task?.let {
                    repository.updateTask(it.copy(isCompleted = !it.isCompleted))
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = TasksUiState.Error(e.message ?: "Failed to toggle task.")
            }
        }
    }
    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            try {
                repository.deleteTask(task)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = TasksUiState.Error(e.message ?: "Failed to delete task.")
            }
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            try {
                repository.updateTask(task)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = TasksUiState.Error(e.message ?: "Failed to update task.")
            }
        }
    }

    fun resetUiState() {
        loadTasks()
    }
}