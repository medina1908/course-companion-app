package com.example.coursecompanionapp.model.repository

import com.example.coursecompanionapp.model.HardcodedData
import com.example.coursecompanionapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotesRepository @Inject constructor() {

    suspend fun getNotes(): List<Note> = withContext(Dispatchers.IO) {
        delay(1000)
        HardcodedData.notes
    }

    suspend fun addNote(note: Note, notes: List<Note>): List<Note> = withContext(Dispatchers.IO) {
        delay(500)
        notes + note
    }
}