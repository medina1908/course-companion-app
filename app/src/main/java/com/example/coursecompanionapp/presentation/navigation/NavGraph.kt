package com.example.coursecompanionapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.coursecompanionapp.presentation.ui.screen.courses.CourseDetailScreen
import com.example.coursecompanionapp.presentation.ui.screen.courses.CoursesScreen
import com.example.coursecompanionapp.presentation.ui.screen.home.DashboardScreen
import com.example.coursecompanionapp.presentation.ui.screen.login.LoginScreen
import com.example.coursecompanionapp.presentation.ui.screen.notes.NoteDetailScreen
import com.example.coursecompanionapp.presentation.ui.screen.notes.NotesScreen
import com.example.coursecompanionapp.presentation.ui.screen.profile.ProfileScreen
import com.example.coursecompanionapp.presentation.ui.screen.tasks.TasksScreen
import com.example.coursecompanionapp.presentation.viewmodel.courses.CoursesViewModel
import com.example.coursecompanionapp.presentation.viewmodel.dashboard.DashboardViewModel
import com.example.coursecompanionapp.presentation.viewmodel.login.LoginViewModel
import com.example.coursecompanionapp.presentation.viewmodel.notes.NotesViewModel
import com.example.coursecompanionapp.presentation.viewmodel.profile.ProfileViewModel
import com.example.coursecompanionapp.presentation.viewmodel.tasks.TasksViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                viewModel = hiltViewModel(),
                onNavigate = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(viewModel = hiltViewModel())
        }
        composable(route = Screen.Courses.route) {
            CoursesScreen(
                viewModel = hiltViewModel(),
                onCourseClick = { courseId, courseName ->
                    navController.navigate(Screen.CourseDetail.createRoute(courseId, courseName))
                }
            )
        }
        composable(route = Screen.Notes.route) {
            NotesScreen(
                viewModel = hiltViewModel(),
                onNoteClick = { noteTitle, noteContent ->
                    navController.navigate(Screen.NoteDetail.createRoute(noteTitle, noteContent))
                }
            )
        }
        composable(route = Screen.Tasks.route) {
            TasksScreen(viewModel = hiltViewModel())
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(viewModel = hiltViewModel())
        }
        composable(
            route = Screen.CourseDetail.route,
            arguments = listOf(
                navArgument("courseId") { type = NavType.IntType },
                navArgument("courseName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getInt("courseId") ?: 0
            val courseName = backStackEntry.arguments?.getString("courseName") ?: ""
            CourseDetailScreen(
                courseId = courseId,
                courseName = courseName,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.NoteDetail.route,
            arguments = listOf(
                navArgument("noteTitle") { type = NavType.StringType },
                navArgument("noteContent") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val noteTitle = backStackEntry.arguments?.getString("noteTitle") ?: ""
            val noteContent = backStackEntry.arguments?.getString("noteContent") ?: ""
            NoteDetailScreen(
                noteTitle = noteTitle,
                noteContent = noteContent,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}