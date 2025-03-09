package com.geko.challenge.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.applaudo.androidchallenge01.ui.screen.home.LoginScreen
import kotlinx.serialization.Serializable

@Serializable data object LoginRoute // route to ForYou screen

fun NavController.navigateToLogin(navOptions: NavOptions) = navigate(route = LoginRoute, navOptions)

fun NavGraphBuilder.loginScreen(
    onSignUpClick:() -> Unit,
    onShowSnackbar: suspend (String) -> Unit,
) {
    composable<LoginRoute> {
        LoginScreen(onSignUpClick, onShowSnackbar)
    }
}
