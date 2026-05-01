package com.example.coursecompanionapp.model.repository

import com.example.coursecompanionapp.model.HardcodedData
import com.example.coursecompanionapp.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TasksRepository @Inject constructor() {

    suspend fun getTasks(): List<Task> = withContext(Dispatchers.IO) {
        delay(1000)
        HardcodedData.tasks
    }

    suspend fun addTask(task: Task, tasks: List<Task>): List<Task> = withContext(Dispatchers.IO) {
        delay(500)
        tasks + task
    }

    suspend fun toggleTask(taskId: Int, tasks: List<Task>): List<Task> = withContext(Dispatchers.IO) {
        delay(300)
        tasks.map { task ->
            if (task.id == taskId) task.copy(isCompleted = !task.isCompleted)
            else task
        }
    }
}