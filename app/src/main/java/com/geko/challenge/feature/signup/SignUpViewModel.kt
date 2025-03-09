package com.geko.challenge.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geko.challenge.core.ui.SignUpLogicUiEvent
import com.geko.challenge.core.ui.SignUpUiState
import com.geko.challenge.domain.SignUpUseCase
import com.geko.challenge.domain.common.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    val _uiEvent: MutableStateFlow<SignUpLogicUiEvent?> = MutableStateFlow(null)
    val uiEvent: StateFlow<SignUpLogicUiEvent?> = _uiEvent.asStateFlow()

    val _UiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState.Success())
    val uiState: StateFlow<SignUpUiState> = _UiState.asStateFlow()

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            var state = _UiState.value
            if (state is SignUpUiState.Success) {
                _UiState.value = state.copy(isLoading = true)

                when(val result = signUpUseCase(firstName, lastName, email, password)) {
                    is UseCaseResult.Failed -> {
                        _UiState.value = state.copy(isLoading = false)

                        val event = _uiEvent.value
                        _uiEvent.value = SignUpLogicUiEvent.onShowSnackBar(
                            result.error,
                            if (event is SignUpLogicUiEvent.onShowSnackBar) {
                                !(event.effectToggle ?: false)
                            } else {
                                null
                            }
                        )
                    }
                    is UseCaseResult.Succeed -> {
                        _UiState.value = state.copy(isLoading = false)
                        // no need to navigate as if user authenticates then app state changes,
                        // meaning it is not this view's state that changes
                    }
                }
            }
        }
    }
}
