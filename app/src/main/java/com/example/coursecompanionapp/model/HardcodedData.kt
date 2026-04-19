package com.example.coursecompanionapp.model

object HardcodedData {

    val courses = listOf(
        Course(1, "Mobile Programming", "Prof. Vatres", 6),
        Course(2, "Database Systems", "Prof. Isakovic", 6),
        Course(3, "Algorithms", "Prof. Mehanovic", 5)
    )

    val notes = listOf(
        Note(1, "Jetpack Compose Basics", "Column, Row, and Box are the main layout elements in Jetpack Compose. Column arranges children vertically, Row arranges them horizontally, and Box allows stacking. These are fundamental building blocks for any Compose UI.", 1, "25.03.2026"),
        Note(2, "SQL JOIN Types", "INNER JOIN returns records that have matching values in both tables. LEFT JOIN returns all records from the left table and matched records from the right table. RIGHT JOIN does the opposite. FULL JOIN returns all records when there is a match in either table.", 2, "26.03.2026"),
        Note(3, "Kotlin Functions", "Functions in Kotlin are first class citizens. They can be stored in variables, passed as arguments, and returned from other functions. Kotlin supports both regular functions and lambda expressions.", 1, "24.03.2026"),
        Note(4, "MVVM Architecture", "Model View ViewModel pattern separates UI from business logic. The Model handles data, the View displays UI, and the ViewModel manages state and business logic. This makes code more testable and maintainable.", 2, "23.03.2026")
    )

    val tasks = listOf(
        Task(1, "Assignment 1 - UI Foundations", 1, "29.03.2026", false),
        Task(2, "Database Project", 2, "15.04.2026", false),
        Task(3, "Midterm Exam", 3, "10.04.2026", true)
    )
}