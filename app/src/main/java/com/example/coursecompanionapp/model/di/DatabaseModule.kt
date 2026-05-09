package com.example.coursecompanionapp.model.di

import android.content.Context
import androidx.room.Room
import com.example.coursecompanionapp.model.data.local.dao.CourseCategoryDao
import com.example.coursecompanionapp.model.data.local.dao.CourseDao
import com.example.coursecompanionapp.model.data.local.dao.NoteDao
import com.example.coursecompanionapp.model.data.local.dao.TaskDao
import com.example.coursecompanionapp.model.data.local.dao.UserDao
import com.example.coursecompanionapp.model.data.local.db.AppDatabase
import com.example.coursecompanionapp.model.repository.CourseRepository
import com.example.coursecompanionapp.model.repository.CourseRepositoryImpl
import com.example.coursecompanionapp.model.repository.NoteRepository
import com.example.coursecompanionapp.model.repository.NoteRepositoryImpl
import com.example.coursecompanionapp.model.repository.TaskRepository
import com.example.coursecompanionapp.model.repository.TaskRepositoryImpl
import com.example.coursecompanionapp.model.repository.UserRepository
import com.example.coursecompanionapp.model.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "course_companion_db"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideCourseDao(database: AppDatabase): CourseDao {
        return database.courseDao()
    }

    @Provides
    @Singleton
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao()
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideCourseCategoryDao(database: AppDatabase): CourseCategoryDao {
        return database.courseCategoryDao()
    }

    @Provides
    @Singleton
    fun provideCourseRepository(courseDao: CourseDao): CourseRepository {
        return CourseRepositoryImpl(courseDao)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }
}