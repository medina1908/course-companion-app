package com.example.coursecompanionapp.presentation.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursecompanionapp.model.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Init)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<ProfileNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                _uiState.value = ProfileUiState.Success(
                    name = "Student",
                    email = "student@stu.ibu.edu.ba",
                    university = "International Burch University",
                    department = "Software Engineering"
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.message ?: "Failed to load profile.")
            }
        }
    }

    fun resetUiState() {
        loadProfile()
    }
}