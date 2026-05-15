package com.example.coursecompanionapp.model.data.local.dao

import androidx.room.*
import com.example.coursecompanionapp.model.data.local.entity.CourseCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CourseCategoryEntity): Long

    @Update
    suspend fun updateCategory(category: CourseCategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CourseCategoryEntity)

    @Query("SELECT * FROM course_categories")
    fun getAllCategories(): Flow<List<CourseCategoryEntity>>

    @Query("SELECT * FROM course_categories WHERE id = :id")
    suspend fun getCategoryById(id: Int): CourseCategoryEntity?

    @Query("DELETE FROM course_categories")
    suspend fun deleteAllCategories()
}