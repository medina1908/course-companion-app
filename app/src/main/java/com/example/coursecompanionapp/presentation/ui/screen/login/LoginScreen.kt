package com.example.coursecompanionapp.presentation.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.presentation.theme.CourseCompanionAppTheme
import com.example.coursecompanionapp.presentation.ui.screen.login.component.LoginButton

private fun isEmailValid(email: String) = email.contains("stu.ibu.edu.ba") && email.isNotBlank()
private fun isPasswordValid(password: String) = password.length >= 8

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    LoginScreen(
        email = email,
        password = password,
        emailError = emailError,
        passwordError = passwordError,
        onEmailChange = {
            email = it
            emailError = false
        },
        onPasswordChange = {
            password = it
            passwordError = false
        },
        onLoginClick = {
            emailError = !isEmailValid(email)
            passwordError = !isPasswordValid(password)
            if (isEmailValid(email) && isPasswordValid(password)) {
                onLoginClick()
            }
        },
        isLoginEnabled = isEmailValid(email) && isPasswordValid(password),
        modifier = modifier
    )
}


@Composable
private fun LoginScreen(
    email: String,
    password: String,
    emailError: Boolean,
    passwordError: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    isLoginEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary,
                        Color(0xFFF5F7FA)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            Text(
                text = "📚",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            Text(
                text = "Course Companion",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "International Burch University",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                shape = RoundedCornerShape(dimensionResource(R.dimen.card_radius)),
                elevation = CardDefaults.cardElevation(dimensionResource(R.dimen.card_elevation))
            ) {
                Column(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome Back! 👋",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Sign in to continue",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                    OutlinedTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        label = { Text("Email") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        isError = emailError,
                        supportingText = {
                            if (emailError) Text(
                                "Please enter a valid IBU email!",
                                color = MaterialTheme.colorScheme.error
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(dimensionResource(R.dimen.card_radius))
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                    OutlinedTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        isError = passwordError,
                        supportingText = {
                            if (passwordError) Text(
                                "Password must be at least 8 characters!",
                                color = MaterialTheme.colorScheme.error
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(dimensionResource(R.dimen.card_radius))
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                    LoginButton(
                        enabled = isLoginEnabled,
                        onClick = onLoginClick
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                    Text(
                        text = "Use your IBU student credentials",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    CourseCompanionAppTheme {
        LoginScreen()
    }
}