package com.example.coursecompanionapp.presentation.viewmodel.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursecompanionapp.model.data.local.entity.NoteEntity
import com.example.coursecompanionapp.model.repository.NoteRepository
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
    private val repository: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<NotesUiState>(NotesUiState.Init)
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<NotesNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    private val _notes = MutableStateFlow<List<NoteEntity>>(emptyList())
    val notes: StateFlow<List<NoteEntity>> = _notes.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _uiState.value = NotesUiState.Loading
            try {
                repository.getAllNotes().collect { notes ->
                    _notes.value = notes
                    _uiState.value = NotesUiState.Success(notes)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = NotesUiState.Error(e.message ?: "Failed to load notes.")
            }
        }
    }

    fun addNote(note: NoteEntity) {
        viewModelScope.launch {
            try {
                repository.insertNote(note)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = NotesUiState.Error(e.message ?: "Failed to add note.")
            }
        }
    }
    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            try {
                repository.deleteNote(note)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = NotesUiState.Error(e.message ?: "Failed to delete note.")
            }
        }
    }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch {
            try {
                repository.updateNote(note)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = NotesUiState.Error(e.message ?: "Failed to update note.")
            }
        }
    }

    fun resetUiState() {
        loadNotes()
    }
}