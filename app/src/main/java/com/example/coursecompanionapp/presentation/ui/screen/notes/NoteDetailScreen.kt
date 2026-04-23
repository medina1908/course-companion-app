package com.example.coursecompanionapp.presentation.ui.screen.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.presentation.theme.CourseCompanionAppTheme

@Composable
fun NoteDetailScreen(
    noteTitle: String,
    noteContent: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NoteDetailScreen(
        noteTitle = noteTitle,
        noteContent = noteContent,
        onBackClick = onBackClick,
        modifier = modifier,
        isStateless = true
    )
}

@Composable
private fun NoteDetailScreen(
    noteTitle: String,
    noteContent: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    isStateless: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                Text(
                    text = noteTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(R.dimen.card_radius)),
                elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.card_elevation))
            ) {
                Column(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(
                        text = noteTitle,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                    Text(
                        text = noteContent,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NoteDetailScreenPreview() {
    CourseCompanionAppTheme {
        NoteDetailScreen(
            noteTitle = "Jetpack Compose Basics",
            noteContent = "Column, Row, Box are main layout elements. This is a very long content that should be displayed fully without any truncation. We want to see all the text here.",
            onBackClick = {}
        )
    }
}