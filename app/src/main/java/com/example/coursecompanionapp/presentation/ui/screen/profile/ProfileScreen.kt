package com.example.coursecompanionapp.presentation.ui.screen.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.presentation.theme.CourseCompanionAppTheme
import com.example.coursecompanionapp.presentation.ui.screen.error.ErrorScreen
import com.example.coursecompanionapp.presentation.ui.screen.loading.LoadingScreen
import com.example.coursecompanionapp.presentation.ui.screen.profile.component.AccountInfo
import com.example.coursecompanionapp.presentation.ui.screen.profile.component.NotificationSettings
import com.example.coursecompanionapp.presentation.ui.screen.profile.component.ProfileHeader
import com.example.coursecompanionapp.presentation.viewmodel.profile.ProfileUiState
import com.example.coursecompanionapp.presentation.viewmodel.profile.ProfileViewModel

// STATEFUL
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var notificationsEnabled by remember { mutableStateOf(true) }
    var emailUpdates by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    when (uiState) {
        is ProfileUiState.Loading -> {
            LoadingScreen()
        }
        is ProfileUiState.Error -> {
            ErrorScreen(
                errorMessage = (uiState as ProfileUiState.Error).message,
                onErrorClick = { viewModel.resetUiState() }
            )
        }
        is ProfileUiState.Success -> {
            val successState = uiState as ProfileUiState.Success
            ProfileScreen(
                name = successState.name,
                email = successState.email,
                university = successState.university,
                department = successState.department,
                notificationsEnabled = notificationsEnabled,
                emailUpdates = emailUpdates,
                showLogoutDialog = showLogoutDialog,
                onNotificationsChange = { notificationsEnabled = it },
                onEmailUpdatesChange = { emailUpdates = it },
                onShowLogoutDialog = { showLogoutDialog = true },
                onDismissLogoutDialog = { showLogoutDialog = false },
                modifier = modifier
            )
        }
        else -> {}
    }
}

// STATELESS
@Composable
private fun ProfileScreen(
    name: String,
    email: String,
    university: String,
    department: String,
    notificationsEnabled: Boolean,
    emailUpdates: Boolean,
    showLogoutDialog: Boolean,
    onNotificationsChange: (Boolean) -> Unit,
    onEmailUpdatesChange: (Boolean) -> Unit,
    onShowLogoutDialog: () -> Unit,
    onDismissLogoutDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = onDismissLogoutDialog,
            title = { Text("Log out?") },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                Button(
                    onClick = onDismissLogoutDialog,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) { Text("Log Out") }
            },
            dismissButton = {
                TextButton(onClick = onDismissLogoutDialog) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ProfileHeader(
            name = name,
            university = university
        )

        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
            Text(
                text = "Notifications",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(
                    top = dimensionResource(R.dimen.padding_small),
                    bottom = dimensionResource(R.dimen.padding_small)
                )
            )

            NotificationSettings(
                notificationsEnabled = notificationsEnabled,
                emailUpdates = emailUpdates,
                onNotificationsChange = onNotificationsChange,
                onEmailUpdatesChange = onEmailUpdatesChange
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            Text(
                text = "Account Info",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
            )

            AccountInfo(
                email = email,
                department = department
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            OutlinedButton(
                onClick = onShowLogoutDialog,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                border = BorderStroke(
                    dimensionResource(R.dimen.padding_small).div(8),
                    MaterialTheme.colorScheme.error
                )
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = null)
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                Text("Log Out")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    CourseCompanionAppTheme {
        ProfileScreen(
            name = "Medina Alić",
            email = "medina@stu.ibu.edu.ba",
            university = "International Burch University",
            department = "Software Engineering",
            notificationsEnabled = true,
            emailUpdates = false,
            showLogoutDialog = false,
            onNotificationsChange = {},
            onEmailUpdatesChange = {},
            onShowLogoutDialog = {},
            onDismissLogoutDialog = {}
        )
    }
}