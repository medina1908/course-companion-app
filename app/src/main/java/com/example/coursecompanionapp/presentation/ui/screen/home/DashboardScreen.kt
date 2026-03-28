package com.example.coursecompanionapp.presentation.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.presentation.theme.CourseCompanionAppTheme
import com.example.coursecompanionapp.presentation.ui.component.InfoSection
import com.example.coursecompanionapp.presentation.ui.component.UserSectionCard
import com.example.coursecompanionapp.presentation.ui.screen.home.component.DashboardHeader
import com.example.coursecompanionapp.presentation.util.InfoRowData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
) {
    val todaysTasks = listOf(
        InfoRowData(stringResource(R.string.todays_tasks), imageVector = Icons.Default.Star),
        InfoRowData("Complete Assignment", imageVector = Icons.Default.Star),
        InfoRowData("Read Docs", imageVector = Icons.Default.Star)
    )

    val achievements = listOf(
        InfoRowData("First Course Added", imageVector = Icons.Default.Face),
        InfoRowData("3 Day Streak", imageVector = Icons.Default.Face)
    )

    val stats = listOf(
        InfoRowData("Courses", additionalInfo = "3"),
        InfoRowData("Notes", additionalInfo = "2"),
        InfoRowData("Tasks", additionalInfo = "3")
    )

    Scaffold(
        topBar = { DashboardHeader() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

            UserSectionCard(
                totalCourses = 3,
                totalTasks = 3,
                totalNotes = 2
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            InfoSection(
                title = stringResource(R.string.todays_tasks),
                rows = todaysTasks
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            InfoSection(
                title = stringResource(R.string.achievements),
                rows = achievements
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            InfoSection(
                title = stringResource(R.string.quick_stats),
                rows = stats
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    CourseCompanionAppTheme {
        DashboardScreen()
    }
}