package com.geko.challenge.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.geko.challenge.navigation.ChallengeNavhost

@Composable
fun ChallengeApp(
    appState: AppState,
    modifier: Modifier = Modifier,
    isAuthenticated: Boolean
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val onBackClick: () -> Unit = { appState.navController.popBackStack() }

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
            )
        },
        topBar = {
            if (appState.shouldShowTopBar) {
                val px = WindowInsets.safeDrawing.getTop(LocalDensity.current)
                val dps = with(LocalDensity.current) {
                    px.toDp()
                }

                AppToolbar(
                    modifier = Modifier.fillMaxWidth().height(
                        dps + 40.dp
                    ).background(Color.Blue),
                    onBackClick = onBackClick
                )
            }
        }
    ) { padding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            ChallengeNavhost(
                appState = appState,
                onShowSnackbar = { message ->
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = Short,
                    )
                },
                isAuthenticated = isAuthenticated
            )

        }
    }
}
