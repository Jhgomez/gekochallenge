package com.applaudo.androidchallenge01.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.geko.challenge.core.ui.Login
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geko.challenge.core.ui.LoginLogicUiEvent
import com.geko.challenge.core.ui.LoginUiState

@Composable
fun LoginScreen(
    onUserAuthenticated: (userName : String) -> Unit,
    onSignUpClick: () -> Unit,
    onShowSnackbar: suspend (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEvent by viewModel.uiEvent.collectAsStateWithLifecycle()

    HomeScreenContent(
        onLoginClick = { email, password -> viewModel.login(email, password) },
        onSignUpClick = onSignUpClick,
        onShowSnackbar = onShowSnackbar,
        onUserAuthenticated = onUserAuthenticated,
        uiState = uiState,
        uiEvent = uiEvent,
        modifier = modifier
    )
}

@Composable
fun HomeScreenContent(
    onLoginClick: (String, String) -> Unit,
    onSignUpClick: () -> Unit,
    onShowSnackbar: suspend (String) -> Unit,
    onUserAuthenticated: (userName: String) -> Unit,
    uiState: LoginUiState,
    uiEvent: LoginLogicUiEvent?,
    modifier: Modifier,
) {
    Column(modifier = modifier) {
        Login(
            onLoginClick = onLoginClick,
            onSignUpClick = onSignUpClick,
            uiState = uiState,
            onShowSnackbar = onShowSnackbar,
            onUserAutheticated = onUserAuthenticated,
            uiEvent = uiEvent
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
//    AndroidChallenge01Theme {
//        HomeScreenContent()
//    }
}
