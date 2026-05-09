package com.example.coursecompanionapp.model.repository


import com.example.coursecompanionapp.model.data.local.dao.NoteDao
import com.example.coursecompanionapp.model.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDao.getAllNotes()
    }

    override fun getNotesByCourseId(courseId: Int): Flow<List<NoteEntity>> {
        return noteDao.getNotesByCourseId(courseId)
    }

    override suspend fun getNoteById(id: Int): NoteEntity? {
        return noteDao.getNoteById(id)
    }

    override suspend fun insertNote(note: NoteEntity): Long {
        return noteDao.insertNote(note)
    }

    override suspend fun updateNote(note: NoteEntity) {
        noteDao.updateNote(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        noteDao.deleteNote(note)
    }

    override suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }
}