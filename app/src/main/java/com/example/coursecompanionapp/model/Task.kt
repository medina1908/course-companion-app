package com.example.coursecompanionapp.model

data class Task(
    val id: Int = 0,
    val title: String = "",
    val courseId: Int = 0,
    val dueDate: String = "",
    val isCompleted: Boolean = false
)