package com.example.coursecompanionapp.presentation.viewmodel.login

sealed interface LoginNavigationEvent {
    data object Navigate : LoginNavigationEvent
    data object NavigateBack : LoginNavigationEvent
}