package com.example.coursecompanionapp.model.repository

import com.example.coursecompanionapp.model.Course
import com.example.coursecompanionapp.model.HardcodedData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoursesRepository @Inject constructor() {

    suspend fun getCourses(): List<Course> = withContext(Dispatchers.IO) {
        delay(1000)
        HardcodedData.courses
    }

    suspend fun addCourse(course: Course, courses: List<Course>): List<Course> = withContext(Dispatchers.IO) {
        delay(500)
        courses + course
    }
}