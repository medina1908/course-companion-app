package com.example.coursecompanionapp.model.repository

import com.example.coursecompanionapp.model.data.local.dao.CourseDao
import com.example.coursecompanionapp.model.data.local.entity.CourseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val courseDao: CourseDao
) : CourseRepository {

    override fun getAllCourses(): Flow<List<CourseEntity>> {
        return courseDao.getAllCourses()
    }

    override suspend fun getCourseById(id: Int): CourseEntity? {
        return courseDao.getCourseById(id)
    }

    override suspend fun insertCourse(course: CourseEntity): Long {
        return courseDao.insertCourse(course)
    }

    override suspend fun updateCourse(course: CourseEntity) {
        courseDao.updateCourse(course)
    }

    override suspend fun deleteCourse(course: CourseEntity) {
        courseDao.deleteCourse(course)
    }

    override suspend fun deleteAllCourses() {
        courseDao.deleteAllCourses()
    }
}