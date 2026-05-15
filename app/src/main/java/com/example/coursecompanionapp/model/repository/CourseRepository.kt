package com.example.coursecompanionapp.model.repository



import com.example.coursecompanionapp.model.data.local.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getAllCourses(): Flow<List<CourseEntity>>
    suspend fun getCourseById(id: Int): CourseEntity?
    suspend fun insertCourse(course: CourseEntity): Long
    suspend fun updateCourse(course: CourseEntity)
    suspend fun deleteCourse(course: CourseEntity)
    suspend fun deleteAllCourses()
}