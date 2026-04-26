package com.example.coursecompanionapp.presentation.ui.screen.courses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.model.Course
import com.example.coursecompanionapp.presentation.theme.CourseCompanionAppTheme
import com.example.coursecompanionapp.presentation.ui.screen.courses.component.CourseItem
import com.example.coursecompanionapp.presentation.viewmodel.courses.CoursesViewModel

private fun isCourseFormValid(name: String, professor: String, credits: String) =
    name.isNotBlank() && professor.isNotBlank() && credits.isNotBlank()

@Composable
fun CoursesScreen(
    onCourseClick: (Int, String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier,
    viewModel: CoursesViewModel = viewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    CoursesScreen(
        courses = state.courses.filter {
            it.name.contains(state.searchQuery, ignoreCase = true) ||
                    it.professor.contains(state.searchQuery, ignoreCase = true)
        },
        showForm = state.showForm,
        courseName = state.courseName,
        professor = state.professor,
        credits = state.credits,
        searchQuery = state.searchQuery,
        onCourseClick = onCourseClick,
        onShowFormToggle = { viewModel.onToggleForm() },
        onCourseNameChange = { viewModel.onCourseNameChange(it) },
        onProfessorChange = { viewModel.onProfessorChange(it) },
        onCreditsChange = { viewModel.onCreditsChange(it) },
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
        onSaveCourse = { viewModel.onSaveCourse() },
        modifier = modifier
    )
}

@Composable
private fun CoursesScreen(
    courses: List<Course>,
    showForm: Boolean,
    courseName: String,
    professor: String,
    credits: String,
    searchQuery: String,
    onCourseClick: (Int, String) -> Unit,
    onShowFormToggle: () -> Unit,
    onCourseNameChange: (String) -> Unit,
    onProfessorChange: (String) -> Unit,
    onCreditsChange: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSaveCourse: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onShowFormToggle,
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

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = dimensionResource(R.dimen.padding_large))
        ) {

            item {
                Text(
                    text = "My Courses",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    label = { Text("Search courses...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = null)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            }

            if (showForm) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                    ) {

                        OutlinedTextField(
                            value = courseName,
                            onValueChange = onCourseNameChange,
                            label = { Text("Course Name") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        OutlinedTextField(
                            value = professor,
                            onValueChange = onProfessorChange,
                            label = { Text("Professor") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        OutlinedTextField(
                            value = credits,
                            onValueChange = onCreditsChange,
                            label = { Text("Credits") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        Button(
                            onClick = onSaveCourse,
                            enabled = isCourseFormValid(courseName, professor, credits),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Save Course")
                        }

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                    }
                }
            }

            if (courses.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_large)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No courses found")
                    }
                }
            } else {
                items(courses) { course ->
                    CourseItem(
                        course = course,
                        onCourseClick = { onCourseClick(course.id, course.name) },
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
fun CoursesScreenPreview() {
    CourseCompanionAppTheme {
        CoursesScreen()
    }
}