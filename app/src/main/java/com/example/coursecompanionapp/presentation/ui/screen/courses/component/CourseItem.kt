package com.example.coursecompanionapp.presentation.ui.screen.courses.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.model.data.local.entity.CourseEntity

@Composable
fun CourseItem(
    course: CourseEntity,
    onCourseClick: () -> Unit = {},
    onDelete: (CourseEntity) -> Unit = {},
    onEdit: (CourseEntity) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_radius)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(R.dimen.card_elevation)
        )
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                )
                .padding(dimensionResource(R.dimen.padding_large))
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = course.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                Text(
                    text = "Prof: ${course.professor}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                Text(
                    text = "Credits: ${course.credits}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onCourseClick) {
                        Text("View", color = Color.White)
                    }
                    IconButton(onClick = { onEdit(course) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { onDelete(course) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}