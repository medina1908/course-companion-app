package com.example.coursecompanionapp.presentation.ui.screen.login

fun isEmailValid(email: String) = email.contains("stu.ibu.edu.ba") && email.isNotBlank()
fun isPasswordValid(password: String) = password.length >= 8