package com.example.coursecompanionapp.model.data.local.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_categories")
data class CourseCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String = "",
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)