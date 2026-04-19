package com.example.coursecompanionapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Task
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem("Home", Icons.Default.Home, Screen.Dashboard.route),
    BottomNavItem("Courses", Icons.Default.Book, Screen.Courses.route),
    BottomNavItem("Notes", Icons.Default.Note, Screen.Notes.route),
    BottomNavItem("Tasks", Icons.Default.Task, Screen.Tasks.route),
    BottomNavItem("Profile", Icons.Default.Person, Screen.Profile.route)
)