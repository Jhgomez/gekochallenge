package com.geko.challenge.feature.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geko.challenge.core.ui.SignUp
import com.geko.challenge.core.ui.SignUpLogicUiEvent
import com.geko.challenge.core.ui.SignUpUiState

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEvent by viewModel.uiEvent.collectAsStateWithLifecycle()

    SignUpScreenContent(
        onRegister = viewModel::register,
        uiState = uiState,
        uiEvent = uiEvent,
        onShowSnackbar = onShowSnackbar,
        modifier = modifier
    )
}

@Composable
fun SignUpScreenContent(
    onRegister: (String, String, String, String) -> Unit,
    uiState: SignUpUiState,
    uiEvent: SignUpLogicUiEvent?,
    modifier: Modifier,
    onShowSnackbar: suspend (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SignUp(
            onRegister = onRegister,
            uiState = uiState,
            uiEvent = uiEvent,
            onShowSnackbar = onShowSnackbar
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun UserScreenPreview() {
//    AndroidChallenge01Theme {
//        HomeScreenContent()
//    }
}
