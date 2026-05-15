package com.example.coursecompanionapp.model.repository


import com.example.coursecompanionapp.model.data.local.dao.TaskDao
import com.example.coursecompanionapp.model.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return taskDao.getAllTasks()
    }

    override fun getTasksByCourseId(courseId: Int): Flow<List<TaskEntity>> {
        return taskDao.getTasksByCourseId(courseId)
    }

    override fun getTasksByCompletion(isCompleted: Boolean): Flow<List<TaskEntity>> {
        return taskDao.getTasksByCompletion(isCompleted)
    }

    override suspend fun getTaskById(id: Int): TaskEntity? {
        return taskDao.getTaskById(id)
    }

    override suspend fun insertTask(task: TaskEntity): Long {
        return taskDao.insertTask(task)
    }

    override suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }

    override suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }
}