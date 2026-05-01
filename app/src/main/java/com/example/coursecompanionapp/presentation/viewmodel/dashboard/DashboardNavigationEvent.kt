package com.example.coursecompanionapp.presentation.viewmodel.dashboard

sealed interface DashboardNavigationEvent {
    data object Navigate : DashboardNavigationEvent
    data object NavigateBack : DashboardNavigationEvent
}