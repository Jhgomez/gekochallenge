package com.geko.challenge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.geko.challenge.feature.login.navigation.LoginRoute
import com.geko.challenge.feature.login.navigation.loginScreen
import com.geko.challenge.feature.signup.navigation.navigateToSignUp
import com.geko.challenge.feature.signup.navigation.signUpScreen
import com.geko.challenge.feature.user.navigation.UserRoute
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
        startDestination = if (isAuthenticated)  UserRoute else LoginRoute,
        modifier = modifier,
    ) {
        if (isAuthenticated) {
            userScreen()
        } else {
            loginScreen(
                onSignUpClick = navController::navigateToSignUp,
                onShowSnackbar = onShowSnackbar
            )

            signUpScreen(
                onShowSnackbar = onShowSnackbar
            )
        }
    }
}