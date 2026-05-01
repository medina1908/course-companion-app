package com.example.coursecompanionapp.presentation.viewmodel.profile

sealed interface ProfileNavigationEvent {
    data object Navigate : ProfileNavigationEvent
    data object NavigateBack : ProfileNavigationEvent
}