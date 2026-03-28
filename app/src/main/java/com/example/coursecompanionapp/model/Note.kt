package com.example.coursecompanionapp.model

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val courseId: Int,
    val date: String = "27.03.2026"
)