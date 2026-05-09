package com.example.coursecompanionapp.model.data.local.dao

import androidx.room.*
import com.example.coursecompanionapp.model.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskEntity?

    @Query("SELECT * FROM tasks WHERE course_id = :courseId")
    fun getTasksByCourseId(courseId: Int): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE is_completed = :isCompleted")
    fun getTasksByCompletion(isCompleted: Boolean): Flow<List<TaskEntity>>

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}