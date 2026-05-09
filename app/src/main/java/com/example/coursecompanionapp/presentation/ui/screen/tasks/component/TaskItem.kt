package com.example.coursecompanionapp.presentation.ui.screen.tasks.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.model.data.local.entity.TaskEntity

@Composable
fun TaskItem(
    task: TaskEntity,
    onToggle: (Int) -> Unit,
    onDelete: (TaskEntity) -> Unit,
    onEdit: (TaskEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.card_radius)),
        elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.card_elevation)),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted)
                Color(0xFFFFE0B2)
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggle(task.id) },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (task.isCompleted)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None,
                    color = if (task.isCompleted) Color.Gray else Color(0xFF1A1A1A)
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small).div(2)))
                Text(
                    text = "Due: ${task.dueDate}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = { onEdit(task) }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = { onDelete(task) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}