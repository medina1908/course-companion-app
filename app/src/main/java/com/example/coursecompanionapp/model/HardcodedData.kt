package com.example.coursecompanionapp.model

object HardcodedData {

    val courses = listOf(
        Course(1, "Mobile Programming", "Prof. Vatres", 6),
        Course(2, "Database Systems", "Prof. Isakovic", 6),
        Course(3, "Algorithms", "Prof. Mehanovic", 5)
    )

    val notes = listOf(
        Note(1, "Jetpack Compose Basics", "Column, Row, Box are main layout elements...", 1, "25.03.2026"),
        Note(2, "SQL JOIN Types", "INNER JOIN, LEFT JOIN, RIGHT JOIN...", 2, "26.03.2026"),
        Note(3, "Kotlin Functions", "Functions in Kotlin are first class citizens...", 1, "24.03.2026"),
        Note(4, "MVVM Architecture", "Model View ViewModel pattern separates UI from logic...", 2, "23.03.2026")
    )

    val tasks = listOf(
        Task(1, "Assignment 1 - UI Foundations", 1, "29.03.2026", false),
        Task(2, "Database Project", 2, "15.04.2026", false),
        Task(3, "Midterm Exam", 3, "10.04.2026", true)
    )
}