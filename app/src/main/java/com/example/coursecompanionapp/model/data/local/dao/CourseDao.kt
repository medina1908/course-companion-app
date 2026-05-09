package com.example.coursecompanionapp.model.data.local.dao


import androidx.room.*
import com.example.coursecompanionapp.model.data.local.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity): Long

    @Update
    suspend fun updateCourse(course: CourseEntity)

    @Delete
    suspend fun deleteCourse(course: CourseEntity)

    @Query("SELECT * FROM courses")
    fun getAllCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE id = :id")
    suspend fun getCourseById(id: Int): CourseEntity?

    @Query("DELETE FROM courses")
    suspend fun deleteAllCourses()
}