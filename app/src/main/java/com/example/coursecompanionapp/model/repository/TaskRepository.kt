package com.example.coursecompanionapp.model.repository


import com.example.coursecompanionapp.model.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<TaskEntity>>
    fun getTasksByCourseId(courseId: Int): Flow<List<TaskEntity>>
    fun getTasksByCompletion(isCompleted: Boolean): Flow<List<TaskEntity>>
    suspend fun getTaskById(id: Int): TaskEntity?
    suspend fun insertTask(task: TaskEntity): Long
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
    suspend fun deleteAllTasks()
}