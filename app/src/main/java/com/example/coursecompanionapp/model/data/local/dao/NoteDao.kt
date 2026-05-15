package com.example.coursecompanionapp.model.data.local.dao

import androidx.room.*
import com.example.coursecompanionapp.model.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Query("SELECT * FROM notes WHERE course_id = :courseId")
    fun getNotesByCourseId(courseId: Int): Flow<List<NoteEntity>>

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}