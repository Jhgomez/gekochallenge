package com.applaudo.androidchallenge01.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geko.challenge.core.ui.LoginLogicUiEvent
import com.geko.challenge.core.ui.LoginUiState
import com.geko.challenge.domain.LoginUseCase
import com.geko.challenge.domain.common.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiEvent: MutableStateFlow<LoginLogicUiEvent?> = MutableStateFlow(null)
    val uiEvent: StateFlow<LoginLogicUiEvent?> = _uiEvent.asStateFlow()

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Success)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            when (
                val result = loginUseCase.invoke(email, password)
            ) {
                is UseCaseResult.Failed -> {
                    val event = _uiEvent.value
                    _uiEvent.value = LoginLogicUiEvent.ShowSnackBar(
                        result.error,
                        if (event is LoginLogicUiEvent.ShowSnackBar) {
                            !(event.effectToggle ?: false)
                        } else {
                            null
                        }
                    )
                }
                is UseCaseResult.Succeed -> {
                    // no need to anything as if user authenticates then app state changes,
                    // it is not this view state that changes
                }
            }

        }
    }
}
