package com.example.coursecompanionapp.model.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coursecompanionapp.model.data.local.dao.CourseCategoryDao
import com.example.coursecompanionapp.model.data.local.dao.CourseDao
import com.example.coursecompanionapp.model.data.local.dao.NoteDao
import com.example.coursecompanionapp.model.data.local.dao.TaskDao
import com.example.coursecompanionapp.model.data.local.dao.UserDao
import com.example.coursecompanionapp.model.data.local.entity.CourseCategoryEntity
import com.example.coursecompanionapp.model.data.local.entity.CourseEntity
import com.example.coursecompanionapp.model.data.local.entity.NoteEntity
import com.example.coursecompanionapp.model.data.local.entity.TaskEntity
import com.example.coursecompanionapp.model.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        CourseEntity::class,
        NoteEntity::class,
        TaskEntity::class,
        CourseCategoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun courseDao(): CourseDao
    abstract fun noteDao(): NoteDao
    abstract fun taskDao(): TaskDao
    abstract fun courseCategoryDao(): CourseCategoryDao
}