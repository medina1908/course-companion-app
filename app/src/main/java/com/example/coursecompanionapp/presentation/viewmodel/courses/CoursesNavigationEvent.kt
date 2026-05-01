package com.example.coursecompanionapp.presentation.viewmodel.courses

sealed interface CoursesNavigationEvent {
    data object Navigate : CoursesNavigationEvent
    data object NavigateBack : CoursesNavigationEvent
}