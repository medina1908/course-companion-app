package com.example.coursecompanionapp.presentation.viewmodel.notes

import com.example.coursecompanionapp.model.data.local.entity.NoteEntity

sealed interface NotesUiState {
    data object Init : NotesUiState
    data object Loading : NotesUiState
    data class Success(val notes: List<NoteEntity>) : NotesUiState
    data class Error(val message: String) : NotesUiState
}