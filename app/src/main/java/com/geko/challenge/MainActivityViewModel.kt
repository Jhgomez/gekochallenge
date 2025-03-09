package com.geko.challenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geko.challenge.core.data.repository.AuthenticationRepository
import com.geko.challenge.MainActivityUiState.Success
import com.geko.challenge.MainActivityUiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = authenticationRepository.isAuthenticated
        .map {
            Success(it)
        }
        .stateIn(
        scope = viewModelScope,
        initialValue = Loading,
        started = SharingStarted.WhileSubscribed(5_000),
    )
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState

    data class Success(val isAuthenticated: Boolean) : MainActivityUiState

    fun shouldKeepSplashScreen() = this is Loading

    fun isUserAuthenticated() = if (this is Success) {
        isAuthenticated
    } else {
        false
    }
}
