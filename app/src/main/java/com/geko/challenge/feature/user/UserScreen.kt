package com.geko.challenge.feature.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.applaudo.androidchallenge01.ui.screen.home.UserViewModel
import com.geko.challenge.core.ui.User
import com.geko.challenge.core.ui.UserLogicUiEvent
import com.geko.challenge.core.ui.UserUiState

@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getUserData()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEvent by viewModel.uiEvent.collectAsStateWithLifecycle()

    UserScreenContent(
        onLogout = viewModel::logout,
        uiState =  uiState,
        uiEvent = uiEvent,
        modifier = modifier
    )
}

@Composable
fun UserScreenContent(
    onLogout: () -> Unit,
    uiState: UserUiState,
    uiEvent: UserLogicUiEvent?,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        User(
            onLogout = onLogout,
            uiState = uiState,
            uiEvent = uiEvent
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
