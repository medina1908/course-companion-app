package com.example.coursecompanionapp.presentation.viewmodel.notes

sealed interface NotesNavigationEvent {
    data object Navigate : NotesNavigationEvent
    data object NavigateBack : NotesNavigationEvent
}