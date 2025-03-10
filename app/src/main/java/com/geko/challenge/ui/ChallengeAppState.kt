package com.geko.challenge.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.geko.challenge.feature.login.navigation.LoginRoute
import com.geko.challenge.feature.user.navigation.UserRoute

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
): AppState {
    return remember(
        navController
    ) {
        AppState(
            navController = navController
        )
    }
}

@Stable
class AppState(
    val navController: NavHostController
) {
    private val previousDestination = mutableStateOf<NavDestination?>(null)

    val currentDestination: NavDestination?
        @Composable get() {
            // Collect the currentBackStackEntryFlow as a state
            val currentEntry = navController.currentBackStackEntryFlow
                .collectAsState(initial = null)

            // Fallback to previousDestination if currentEntry is null
            return currentEntry.value?.destination.also { destination ->
                if (destination != null) {
                    previousDestination.value = destination
                }
            } ?: previousDestination.value
        }

    private val screensWithNoToolbar = listOf(
        LoginRoute::class,
        UserRoute::class
    )

    val shouldShowTopBar: Boolean
        @Composable get() {
            val result = screensWithNoToolbar.firstOrNull { route ->
                currentDestination?.hasRoute(route = route) == true
            }

            return result == null
        }
}
