package com.example.coursecompanionapp.presentation.viewmodel.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursecompanionapp.model.Note
import com.example.coursecompanionapp.model.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<NotesUiState>(NotesUiState.Init)
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<NotesNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _uiState.value = NotesUiState.Loading
            try {
                _notes.value = repository.getNotes()
                _uiState.value = NotesUiState.Success(_notes.value)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = NotesUiState.Error(e.message ?: "Failed to load notes.")
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            _uiState.value = NotesUiState.Loading
            try {
                _notes.value = repository.addNote(note, _notes.value)
                _uiState.value = NotesUiState.Success(_notes.value)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = NotesUiState.Error(e.message ?: "Failed to add note.")
            }
        }
    }

    fun resetUiState() {
        loadNotes()
    }
}