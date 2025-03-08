package com.applaudo.androidchallenge01.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.geko.challenge.core.ui.Login
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geko.challenge.core.ui.LoginLogicUiEvent
import com.geko.challenge.core.ui.LoginUiState

@Composable
fun LoginScreen(
    onSignUpClick: () -> Unit,
    onShowSnackbar: suspend (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEvent by viewModel.uiEvent.collectAsStateWithLifecycle()

    LoginScreenContent(
        onLoginClick = viewModel::login,
        onSignUpClick = onSignUpClick,
        onShowSnackbar = onShowSnackbar,
        uiState = uiState,
        uiEvent = uiEvent,
        modifier = modifier
    )
}

@Composable
fun LoginScreenContent(
    onLoginClick: (String, String) -> Unit,
    onSignUpClick: () -> Unit,
    onShowSnackbar: suspend (String) -> Unit,
    uiState: LoginUiState,
    uiEvent: LoginLogicUiEvent?,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Login(
            onLoginClick = onLoginClick,
            onSignUpClick = onSignUpClick,
            uiState = uiState,
            onShowSnackbar = onShowSnackbar,
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
