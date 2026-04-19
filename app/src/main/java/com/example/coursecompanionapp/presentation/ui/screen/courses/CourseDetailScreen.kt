package com.example.coursecompanionapp.presentation.ui.screen.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.presentation.theme.CourseCompanionAppTheme

@Composable
fun CourseDetailScreen(
    courseId: Int,
    courseName: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CourseDetailScreen(
        courseId = courseId,
        courseName = courseName,
        onBackClick = onBackClick,
        modifier = modifier,
        isStateless = true
    )
}


@Composable
private fun CourseDetailScreen(
    courseId: Int,
    courseName: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    isStateless: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                )
                .padding(dimensionResource(R.dimen.padding_large))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                Text(
                    text = courseName,
                    style = MaterialTheme.typography.headlineMedium,
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
                        text = "Course Details",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Course ID",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = courseId.toString(),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Course Name",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = courseName,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CourseDetailScreenPreview() {
    CourseCompanionAppTheme {
        CourseDetailScreen(
            courseId = 1,
            courseName = "Mobile Programming",
            onBackClick = {}
        )
    }
}