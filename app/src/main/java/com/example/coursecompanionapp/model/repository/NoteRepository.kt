package com.example.coursecompanionapp.model.repository


import com.example.coursecompanionapp.model.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<NoteEntity>>
    fun getNotesByCourseId(courseId: Int): Flow<List<NoteEntity>>
    suspend fun getNoteById(id: Int): NoteEntity?
    suspend fun insertNote(note: NoteEntity): Long
    suspend fun updateNote(note: NoteEntity)
    suspend fun deleteNote(note: NoteEntity)
    suspend fun deleteAllNotes()
}