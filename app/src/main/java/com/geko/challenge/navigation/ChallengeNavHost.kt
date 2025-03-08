package com.geko.challenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.geko.challenge.feature.login.navigation.LoginBaseRoute
import com.geko.challenge.feature.login.navigation.loginScreen
import com.geko.challenge.feature.user.navigation.UserBaseRoute
import com.geko.challenge.feature.user.navigation.userScreen
import com.geko.challenge.ui.AppState

@Composable
fun ChallengeNavhost (
    appState: AppState,
    onShowSnackbar: suspend (String) -> Unit,
    modifier: Modifier = Modifier,
    isAuthenticated: Boolean,
) {

    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated)  UserBaseRoute else LoginBaseRoute,
        modifier = modifier,
    ) {
        loginScreen(
            onSignUpClick = {}, //navcontroller::navigateToSignUp
            onShowSnackbar = onShowSnackbar
        )

        userScreen()
    }
}