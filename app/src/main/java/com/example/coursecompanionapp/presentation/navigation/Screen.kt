package com.example.coursecompanionapp.presentation.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login_screen")
    data object Dashboard : Screen("dashboard_screen")
    data object Courses : Screen("courses_screen")
    data object Notes : Screen("notes_screen")
    data object Tasks : Screen("tasks_screen")
    data object Profile : Screen("profile_screen")
    data object CourseDetail : Screen("course_detail_screen/{courseId}/{courseName}") {
        fun createRoute(courseId: Int, courseName: String) =
            "course_detail_screen/$courseId/$courseName"
    }
    data object NoteDetail : Screen("note_detail_screen/{noteTitle}/{noteContent}") {
        fun createRoute(noteTitle: String, noteContent: String) =
            "note_detail_screen/$noteTitle/$noteContent"
    }
}