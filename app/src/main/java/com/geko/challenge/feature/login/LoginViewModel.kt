package com.applaudo.androidchallenge01.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geko.challenge.core.data.repository.AuthenticationRepository
import com.geko.challenge.core.ui.LoginLogicUiEvent
import com.geko.challenge.core.ui.LoginUiState
import com.geko.challenge.domain.LoginUseCase
import com.geko.challenge.domain.common.UseCaseResult
//import com.applaudo.androidchallenge01.R
//import com.applaudo.androidchallenge01.util.Constants.AIRING_TODAY
//import com.applaudo.androidchallenge01.util.Constants.ON_THE_AIR
//import com.applaudo.androidchallenge01.util.Constants.POPULAR
//import com.applaudo.androidchallenge01.util.Constants.TOP_RATED
//import com.applaudo.domain.interactor.GetTvShowsUseCase
//import com.applaudo.domain.model.Category
//import com.applaudo.domain.model.TvShow
//import com.applaudo.domain.util.Status
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val emailError = MutableStateFlow<Boolean>(false)
    private val passwordError = MutableStateFlow<Boolean>(false)

    private val _uiEvent: MutableStateFlow<LoginLogicUiEvent?> = MutableStateFlow(null)
    val uiEvent: StateFlow<LoginLogicUiEvent?> = _uiEvent.asStateFlow()

    val uiState: StateFlow<LoginUiState> =
        combine(
            emailError,
            passwordError
        ) { emailE, passwordE ->
            LoginUiState.Success(
                emailError = emailE,
                passwordError = passwordE
            )
        }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = LoginUiState.Loading,
            )

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
                    _uiEvent.value = LoginLogicUiEvent.NavigateToUserScreen(result.data.name)
                }
            }

        }
    }
}
