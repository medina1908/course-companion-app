package com.example.coursecompanionapp.presentation.ui.screen.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.coursecompanionapp.presentation.ui.screen.courses.component.CourseItem
import com.example.coursecompanionapp.viewmodel.CourseViewModel

@Composable
fun CoursesScreen(
    viewModel: CourseViewModel,
    modifier: Modifier = Modifier
) {
    val courses by viewModel.courses.collectAsState()
    val courseName by viewModel.courseName.collectAsState()
    val professor by viewModel.professor.collectAsState()
    val credits by viewModel.credits.collectAsState()
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
                    text = stringResource(R.string.courses_title),
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
                            text = stringResource(R.string.add_course),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        OutlinedTextField(
                            value = courseName,
                            onValueChange = { viewModel.onCourseNameChange(it) },
                            label = { Text(stringResource(R.string.course_name)) },
                            modifier = Modifier.fillMaxWidth(),
                            isError = courseName.isBlank() && courseName.isNotEmpty()
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        OutlinedTextField(
                            value = professor,
                            onValueChange = { viewModel.onProfessorChange(it) },
                            label = { Text(stringResource(R.string.professor)) },
                            modifier = Modifier.fillMaxWidth(),
                            isError = professor.isBlank() && professor.isNotEmpty()
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        OutlinedTextField(
                            value = credits,
                            onValueChange = { viewModel.onCreditsChange(it) },
                            label = { Text(stringResource(R.string.credits)) },
                            modifier = Modifier.fillMaxWidth(),
                            isError = credits.isBlank() && credits.isNotEmpty()
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        if (courseName.isNotBlank() && professor.isNotBlank() && credits.isBlank()) {
                            Text(
                                text = "Please enter credits!",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Button(
                            onClick = {
                                viewModel.addCourse()
                                showForm = false
                            },
                            enabled = viewModel.isFormValid(),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(stringResource(R.string.save_course))
                        }
                    }
                }
            }

            if (courses.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_courses),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
                ) {
                    items(courses) { course ->
                        CourseItem(course = course)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CoursesScreenPreview() {
    CourseCompanionAppTheme {
        CoursesScreen(viewModel = viewModel())
    }
}