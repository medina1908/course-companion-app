package com.example.coursecompanionapp.presentation.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.model.data.local.entity.CourseEntity
import com.example.coursecompanionapp.model.data.local.entity.TaskEntity
import com.example.coursecompanionapp.presentation.theme.CourseCompanionAppTheme
import com.example.coursecompanionapp.presentation.ui.component.UserSectionCard
import com.example.coursecompanionapp.presentation.ui.screen.error.ErrorScreen
import com.example.coursecompanionapp.presentation.ui.screen.home.component.DashboardHeader
import com.example.coursecompanionapp.presentation.ui.screen.home.component.StatItem
import com.example.coursecompanionapp.presentation.ui.screen.loading.LoadingScreen
import com.example.coursecompanionapp.presentation.viewmodel.dashboard.DashboardUiState
import com.example.coursecompanionapp.presentation.viewmodel.dashboard.DashboardViewModel

// STATEFUL
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val courses by viewModel.courses.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()

    when (uiState) {
        is DashboardUiState.Loading -> {
            LoadingScreen()
        }
        is DashboardUiState.Error -> {
            ErrorScreen(
                errorMessage = (uiState as DashboardUiState.Error).message,
                onErrorClick = { viewModel.resetUiState() }
            )
        }
        else -> {
            DashboardScreen(
                courses = courses,
                tasks = tasks,
                modifier = modifier
            )
        }
    }
}

// STATELESS
@Composable
private fun DashboardScreen(
    courses: List<CourseEntity>,
    tasks: List<TaskEntity>,
    modifier: Modifier = Modifier
) {
    val completedTasks = tasks.count { it.isCompleted }
    val pendingTasks = tasks.count { !it.isCompleted }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        DashboardHeader()

        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {

            UserSectionCard(
                name = "Student",
                university = "International Burch University",
                totalCourses = courses.size,
                totalTasks = tasks.size,
                totalNotes = 0
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            Text(
                text = "My Courses",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

            if (courses.isEmpty()) {
                Text(
                    text = "No courses yet! Go to Courses tab to add one.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
                    contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.padding_small))
                ) {
                    items(courses) { course ->
                        Card(
                            modifier = Modifier.width(dimensionResource(R.dimen.avatar_size) * 2),
                            shape = RoundedCornerShape(dimensionResource(R.dimen.card_radius)),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                            ) {
                                Text(
                                    text = course.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${course.credits} credits",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            Text(
                text = "My Tasks",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

            if (tasks.isEmpty()) {
                Text(
                    text = "No tasks yet! Go to Tasks tab to add one.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
                    contentPadding = PaddingValues(horizontal = dimensionResource(R.dimen.padding_small))
                ) {
                    items(tasks) { task ->
                        Card(
                            modifier = Modifier.width(dimensionResource(R.dimen.avatar_size) * 2),
                            shape = RoundedCornerShape(dimensionResource(R.dimen.card_radius)),
                            colors = CardDefaults.cardColors(
                                containerColor = if (task.isCompleted)
                                    MaterialTheme.colorScheme.secondaryContainer
                                else
                                    MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                            ) {
                                Text(
                                    text = task.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (task.isCompleted) "✅ Done" else "⏳ ${task.dueDate}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            Text(
                text = "Your Stats",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

            StatItem("Courses", if (courses.isEmpty()) 0f else courses.size / 10f, courses.size.toString())
            StatItem("Tasks", if (tasks.isEmpty()) 0f else completedTasks.toFloat() / tasks.size.toFloat(), "${completedTasks}/${tasks.size}")
            StatItem("Pending", if (tasks.isEmpty()) 0f else pendingTasks.toFloat() / tasks.size.toFloat(), pendingTasks.toString())

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    CourseCompanionAppTheme {
        DashboardScreen(
            courses = emptyList(),
            tasks = emptyList()
        )
    }
}