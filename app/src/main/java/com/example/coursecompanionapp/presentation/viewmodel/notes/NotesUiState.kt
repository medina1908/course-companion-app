package com.example.coursecompanionapp.presentation.viewmodel.notes

import com.example.coursecompanionapp.model.Note

sealed interface NotesUiState {
    data object Init : NotesUiState
    data object Loading : NotesUiState
    data class Success(val notes: List<Note>) : NotesUiState
    data class Error(val message: String) : NotesUiState
}