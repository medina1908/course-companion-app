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
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.model.Course
import com.example.coursecompanionapp.model.HardcodedData
import com.example.coursecompanionapp.presentation.theme.CourseCompanionAppTheme
import com.example.coursecompanionapp.presentation.ui.screen.courses.component.CourseItem

private fun isCourseFormValid(name: String, professor: String, credits: String) =
    name.isNotBlank() && professor.isNotBlank() && credits.isNotBlank()


@Composable
fun CoursesScreen(
    onCourseClick: (Int, String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    val courses = remember {
        mutableStateListOf(*HardcodedData.courses.toTypedArray())
    }

    var showForm by remember { mutableStateOf(false) }
    var courseName by remember { mutableStateOf("") }
    var professor by remember { mutableStateOf("") }
    var credits by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    val filteredCourses = courses.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.professor.contains(searchQuery, ignoreCase = true)
    }

    CoursesScreen(
        courses = filteredCourses,
        showForm = showForm,
        courseName = courseName,
        professor = professor,
        credits = credits,
        searchQuery = searchQuery,
        onCourseClick = onCourseClick,
        onShowFormToggle = { showForm = !showForm },
        onCourseNameChange = { courseName = it },
        onProfessorChange = { professor = it },
        onCreditsChange = { if (it.all { c -> c.isDigit() }) credits = it },
        onSearchQueryChange = { searchQuery = it },
        onSaveCourse = {
            if (isCourseFormValid(courseName, professor, credits)) {
                courses.add(
                    Course(
                        id = courses.size + 1,
                        name = courseName,
                        professor = professor,
                        credits = credits.toInt()
                    )
                )
                courseName = ""
                professor = ""
                credits = ""
                showForm = false
            }
        },
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
                            modifier = Modifier.fillMaxWidth(),
                            isError = courseName.isBlank() && courseName.isNotEmpty()
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        OutlinedTextField(
                            value = professor,
                            onValueChange = onProfessorChange,
                            label = { Text("Professor") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = professor.isBlank() && professor.isNotEmpty()
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        OutlinedTextField(
                            value = credits,
                            onValueChange = onCreditsChange,
                            label = { Text("Credits") },
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
                            onClick = onSaveCourse,
                            enabled = isCourseFormValid(courseName, professor, credits),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
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
                        Text(
                            text = if (searchQuery.isBlank()) "No courses yet! Tap + to add your first course."
                            else "No courses found for \"$searchQuery\"",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                items(
                    items = courses,
                    key = { course -> course.id }
                ) { course ->
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