package com.geko.challenge.feature.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.geko.challenge.feature.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Serializable data object SignUpRoute // route to ForYou screen

fun NavController.navigateToSignUp(navOptions: NavOptionsBuilder.() -> Unit = {}) = navigate(route = SignUpRoute, navOptions)

fun NavGraphBuilder.signUpScreen(onShowSnackbar: suspend (String) -> Unit) {
    composable<SignUpRoute> {
        SignUpScreen(onShowSnackbar = onShowSnackbar)
    }
}
