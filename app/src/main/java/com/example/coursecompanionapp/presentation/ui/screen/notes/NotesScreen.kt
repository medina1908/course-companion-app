package com.example.coursecompanionapp.presentation.ui.screen.notes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.model.data.local.entity.NoteEntity
import com.example.coursecompanionapp.presentation.theme.CourseCompanionAppTheme
import com.example.coursecompanionapp.presentation.ui.screen.notes.component.NoteItem
import com.example.coursecompanionapp.presentation.viewmodel.notes.NotesUiState
import com.example.coursecompanionapp.presentation.viewmodel.notes.NotesViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun isNoteFormValid(title: String, content: String) =
    title.isNotBlank() && content.isNotBlank()

// STATEFUL
@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    onNoteClick: (String, String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val notes by viewModel.notes.collectAsStateWithLifecycle()

    var showForm by remember { mutableStateOf(false) }
    var noteTitle by remember { mutableStateOf("") }
    var noteContent by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }
    var editingNote by remember { mutableStateOf<NoteEntity?>(null) }

    val filteredNotes = notes.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.content.contains(searchQuery, ignoreCase = true)
    }

    when (uiState) {
        is NotesUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is NotesUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = (uiState as NotesUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = { viewModel.resetUiState() }) {
                        Text("Try Again")
                    }
                }
            }
        }
        else -> {
            NotesScreen(
                notes = filteredNotes,
                totalNotes = notes.size,
                showForm = showForm,
                noteTitle = noteTitle,
                noteContent = noteContent,
                searchQuery = searchQuery,
                isEditing = editingNote != null,
                onNoteClick = onNoteClick,
                onShowFormToggle = {
                    showForm = !showForm
                    if (!showForm) {
                        editingNote = null
                        noteTitle = ""
                        noteContent = ""
                    }
                },
                onNoteTitleChange = { noteTitle = it },
                onNoteContentChange = { noteContent = it },
                onSearchQueryChange = { searchQuery = it },
                onSaveNote = {
                    if (isNoteFormValid(noteTitle, noteContent)) {
                        val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
                        if (editingNote != null) {
                            viewModel.updateNote(
                                editingNote!!.copy(
                                    title = noteTitle,
                                    content = noteContent
                                )
                            )
                            editingNote = null
                        } else {
                            viewModel.addNote(
                                NoteEntity(
                                    title = noteTitle,
                                    content = noteContent,
                                    courseId = 1,
                                    date = date
                                )
                            )
                        }
                        noteTitle = ""
                        noteContent = ""
                        showForm = false
                    }
                },
                onDeleteNote = { viewModel.deleteNote(it) },
                onEditNote = { note ->
                    editingNote = note
                    noteTitle = note.title
                    noteContent = note.content
                    showForm = true
                },
                modifier = modifier
            )
        }
    }
}

// STATELESS
@Composable
private fun NotesScreen(
    notes: List<NoteEntity>,
    totalNotes: Int,
    showForm: Boolean,
    noteTitle: String,
    noteContent: String,
    searchQuery: String,
    isEditing: Boolean,
    onNoteClick: (String, String) -> Unit,
    onShowFormToggle: () -> Unit,
    onNoteTitleChange: (String) -> Unit,
    onNoteContentChange: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSaveNote: () -> Unit,
    onDeleteNote: (NoteEntity) -> Unit,
    onEditNote: (NoteEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onShowFormToggle,
                containerColor = MaterialTheme.colorScheme.primary,
                icon = {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                text = {
                    Text(
                        if (showForm) "Close" else "Add Note",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = dimensionResource(R.dimen.padding_large))
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "My Notes",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$totalNotes notes",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    label = { Text("Search notes...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
                )
            }

            if (showForm) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_medium))
                    ) {
                        Text(
                            text = if (isEditing) "Edit Note" else "New Note",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        OutlinedTextField(
                            value = noteTitle,
                            onValueChange = onNoteTitleChange,
                            label = { Text("Title") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        OutlinedTextField(
                            value = noteContent,
                            onValueChange = onNoteContentChange,
                            label = { Text("Content") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        Button(
                            onClick = onSaveNote,
                            enabled = isNoteFormValid(noteTitle, noteContent),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(if (isEditing) "Update Note" else "Save Note")
                        }

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                    }
                }
            }

            if (notes.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_large)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = if (searchQuery.isBlank()) "No notes yet!"
                                else "No notes found for \"$searchQuery\"",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            if (searchQuery.isBlank()) {
                                Text(
                                    text = "Tap Add Note to get started",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            } else {
                itemsIndexed(
                    items = notes,
                    key = { _, note -> note.id }
                ) { index, note ->
                    NoteItem(
                        note = note,
                        index = index,
                        onNoteClick = { onNoteClick(note.title, note.content) },
                        onDelete = { onDeleteNote(note) },
                        onEdit = { onEditNote(note) },
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.padding_medium),
                            vertical = dimensionResource(R.dimen.padding_small)
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotesScreenPreview() {
    CourseCompanionAppTheme {
        NotesScreen(
            notes = emptyList(),
            totalNotes = 0,
            showForm = false,
            noteTitle = "",
            noteContent = "",
            searchQuery = "",
            isEditing = false,
            onNoteClick = { _, _ -> },
            onShowFormToggle = {},
            onNoteTitleChange = {},
            onNoteContentChange = {},
            onSearchQueryChange = {},
            onSaveNote = {},
            onDeleteNote = {},
            onEditNote = {}
        )
    }
}