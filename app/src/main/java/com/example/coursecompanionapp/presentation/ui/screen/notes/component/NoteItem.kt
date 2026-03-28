package com.example.coursecompanionapp.presentation.ui.screen.notes.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.model.Note

val noteColors = listOf(
    Color(0xFFFFD6D6),
    Color(0xFFD6F0FF),
    Color(0xFFD6FFE4),
    Color(0xFFFFF3D6),
    Color(0xFFE8D6FF),
    Color(0xFFFFD6F0),
)

@Composable
fun NoteItem(
    note: Note,
    index: Int = 0,
    modifier: Modifier = Modifier
) {
    val cardColor = noteColors[index % noteColors.size]

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_radius)),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.card_elevation)),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF444444),
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Text(
                text = note.date,
                fontSize = 11.sp,
                color = Color(0xFF888888)
            )
        }
    }
}