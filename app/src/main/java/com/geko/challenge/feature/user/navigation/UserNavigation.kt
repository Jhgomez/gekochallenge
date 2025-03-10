package com.geko.challenge.feature.user.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.geko.challenge.feature.user.UserScreen
import kotlinx.serialization.Serializable

@Serializable data object UserRoute // route to ForYou screen


fun NavController.navigateToUser(navOptions: NavOptionsBuilder.() -> Unit = {}) = navigate(route = UserRoute, navOptions)

fun NavGraphBuilder.userScreen() {
    composable<UserRoute> {
        UserScreen()
    }
}
