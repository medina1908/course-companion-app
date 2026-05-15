package com.example.coursecompanionapp.presentation.ui.screen.error

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.coursecompanionapp.presentation.viewmodel.login.LoginUiState

@Composable
fun ErrorScreen(
    errorMessage: String,
    onErrorClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )
            Button(onClick = onErrorClick) {
                Text("Try Again")
            }
        }
    }
}