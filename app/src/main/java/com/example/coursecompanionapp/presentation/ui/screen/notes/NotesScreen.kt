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
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.model.HardcodedData
import com.example.coursecompanionapp.model.Note
import com.example.coursecompanionapp.presentation.theme.CourseCompanionAppTheme
import com.example.coursecompanionapp.presentation.ui.screen.notes.component.NoteItem

private fun isNoteFormValid(title: String, content: String) =
    title.isNotBlank() && content.isNotBlank()

@Composable
fun NotesScreen(
    onNoteClick: (String, String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    val notes = remember {
        mutableStateListOf(*HardcodedData.notes.toTypedArray())
    }

    var showForm by remember { mutableStateOf(false) }
    var noteTitle by remember { mutableStateOf("") }
    var noteContent by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    val filteredNotes = notes.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.content.contains(searchQuery, ignoreCase = true)
    }

    NotesScreen(
        notes = filteredNotes,
        totalNotes = notes.size,
        showForm = showForm,
        noteTitle = noteTitle,
        noteContent = noteContent,
        searchQuery = searchQuery,
        onNoteClick = onNoteClick,
        onShowFormToggle = { showForm = !showForm },
        onNoteTitleChange = { noteTitle = it },
        onNoteContentChange = { noteContent = it },
        onSearchQueryChange = { searchQuery = it },
        onSaveNote = {
            if (isNoteFormValid(noteTitle, noteContent)) {
                notes.add(
                    Note(
                        id = notes.size + 1,
                        title = noteTitle,
                        content = noteContent,
                        courseId = 1,
                        date = "04.04.2026"
                    )
                )
                noteTitle = ""
                noteContent = ""
                showForm = false
            }
        },
        modifier = modifier
    )
}

// STATELESS
@Composable
private fun NotesScreen(
    notes: List<Note>,
    totalNotes: Int,
    showForm: Boolean,
    noteTitle: String,
    noteContent: String,
    searchQuery: String,
    onNoteClick: (String, String) -> Unit,
    onShowFormToggle: () -> Unit,
    onNoteTitleChange: (String) -> Unit,
    onNoteContentChange: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSaveNote: () -> Unit,
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
                            text = "New Note",
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

                        if (noteTitle.isNotBlank() && noteContent.isBlank()) {
                            Text(
                                text = "Please enter note content!",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Button(
                            onClick = onSaveNote,
                            enabled = isNoteFormValid(noteTitle, noteContent),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Save Note")
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
        NotesScreen()
    }
}