package com.example.coursecompanionapp.presentation.viewmodel.tasks

sealed interface TasksNavigationEvent {
    data object Navigate : TasksNavigationEvent
    data object NavigateBack : TasksNavigationEvent
}