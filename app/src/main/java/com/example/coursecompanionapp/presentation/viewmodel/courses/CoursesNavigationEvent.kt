package com.example.coursecompanionapp.presentation.viewmodel.courses

sealed class CoursesNavigationEvent {

    data class OpenCourseDetail(
        val courseId: Int,
        val courseName: String
    ) : CoursesNavigationEvent()
}