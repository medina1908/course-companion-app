package com.example.coursecompanionapp.presentation.ui.screen.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.presentation.theme.CourseCompanionAppTheme
import com.example.coursecompanionapp.presentation.ui.screen.notes.component.NoteItem
import com.example.coursecompanionapp.viewmodel.CourseViewModel

@Composable
fun NotesScreen(
    viewModel: CourseViewModel,
    modifier: Modifier = Modifier
) {
    val notes by viewModel.notes.collectAsState()
    val noteTitle by viewModel.noteTitle.collectAsState()
    val noteContent by viewModel.noteContent.collectAsState()
    var showForm by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showForm = !showForm },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    if (showForm) "✕" else "+",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(dimensionResource(R.dimen.padding_medium)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.notes_title),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            if (showForm) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.card_radius)),
                    elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.card_elevation))
                ) {
                    Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
                        Text(
                            text = stringResource(R.string.add_note),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        OutlinedTextField(
                            value = noteTitle,
                            onValueChange = { viewModel.onNoteTitleChange(it) },
                            label = { Text(stringResource(R.string.note_title)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        OutlinedTextField(
                            value = noteContent,
                            onValueChange = { viewModel.onNoteContentChange(it) },
                            label = { Text(stringResource(R.string.note_content)) },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        if (noteTitle.isNotBlank() && noteContent.isBlank()) {
                            Text(
                                text = stringResource(R.string.error_enter_content),
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Button(
                            onClick = {
                                viewModel.addNote()
                                showForm = false
                            },
                            enabled = viewModel.isNoteFormValid(),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(stringResource(R.string.save_note))
                        }
                    }
                }
            }

            if (notes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_notes),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
                    verticalItemSpacing = dimensionResource(R.dimen.padding_small)
                ) {
                    itemsIndexed(notes) { index, note ->
                        NoteItem(note = note, index = index)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotesScreenPreview() {
    CourseCompanionAppTheme {
        NotesScreen(viewModel = viewModel())
    }
}