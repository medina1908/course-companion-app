package com.example.coursecompanionapp.presentation.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.presentation.theme.CourseCompanionAppTheme
import com.example.coursecompanionapp.presentation.ui.component.UserSectionCard
import com.example.coursecompanionapp.presentation.ui.screen.home.component.DashboardHeader

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
) {
    val tasks = listOf(
        "Study Kotlin",
        "Complete Assignment",
        "Read Docs"
    )
    val achievements = listOf(
        "First Course Added",
        "3 Day Streak"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        DashboardHeader()

        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {

            UserSectionCard(
                totalCourses = 3,
                totalTasks = 3,
                totalNotes = 2
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            Text(
                text = "Today's Focus",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

            tasks.forEach { task ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                    Text(task)
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            Text(
                text = "Achievements",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                achievements.forEach { achievement ->
                    Box(
                        modifier = Modifier
                            .padding(end = dimensionResource(R.dimen.padding_small))
                            .clip(RoundedCornerShape(dimensionResource(R.dimen.card_radius)))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(dimensionResource(R.dimen.padding_medium))
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Face, contentDescription = null)
                            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                            Text(achievement)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            Text(
                text = "Your Stats",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

            StatItem("Courses", 3f / 5f, "3")
            StatItem("Notes", 2f / 5f, "2")
            StatItem("Tasks", 3f / 5f, "3")

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))
        }
    }
}

@Composable
fun StatItem(
    title: String,
    progress: Float,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = dimensionResource(R.dimen.padding_small))) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title)
            Text(value)
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.padding_small))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.card_radius)))
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    CourseCompanionAppTheme {
        DashboardScreen()
    }
}