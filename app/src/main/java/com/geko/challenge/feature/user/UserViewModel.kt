package com.applaudo.androidchallenge01.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geko.challenge.core.data.repository.AuthenticationRepository
import com.geko.challenge.core.data.repository.UserRepository
import com.geko.challenge.core.ui.LoginLogicUiEvent
import com.geko.challenge.core.ui.LoginUiState
import com.geko.challenge.core.ui.UserLogicUiEvent
import com.geko.challenge.core.ui.UserUiState
import com.geko.challenge.domain.GetUserDataUseCase
import com.geko.challenge.domain.LogoutUseCase
import com.geko.challenge.domain.common.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getUserDataUseCase: GetUserDataUseCase
) : ViewModel() {

    val _uiEvent: MutableStateFlow<UserLogicUiEvent?> = MutableStateFlow(null)
    val uiEvent: StateFlow<UserLogicUiEvent?> = _uiEvent.asStateFlow()

    val _UiState: MutableStateFlow<UserUiState> = MutableStateFlow(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _UiState.asStateFlow()

    fun getUserData() {
        viewModelScope.launch {
            when(val result = getUserDataUseCase()) {
                is UseCaseResult.Failed -> {}
                is UseCaseResult.Succeed -> {
                    _UiState.value = UserUiState.Success(result.data)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}
