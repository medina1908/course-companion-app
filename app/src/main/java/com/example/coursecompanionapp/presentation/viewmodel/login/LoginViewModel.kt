package com.example.coursecompanionapp.presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Init)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<LoginNavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onLoginClick(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                delay(1500)
                if (email.contains("stu.ibu.edu.ba") && password.length >= 8) {
                    _uiState.value = LoginUiState.Success(isLoggedIn = true)
                    _navigationEvent.send(LoginNavigationEvent.Navigate)
                } else {
                    _uiState.value = LoginUiState.Error("Invalid email or password")
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(e.message ?: "Something went wrong")
            }
        }
    }

    fun resetUiState() {
        _uiState.value = LoginUiState.Init
    }
}