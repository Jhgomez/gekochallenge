package com.geko.challenge

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.geko.challenge.core.data.repository.AuthenticationRepository
import com.geko.challenge.core.design.theme.PruebatecTheme
import com.geko.challenge.ui.ChallengeApp
import com.geko.challenge.ui.rememberAppState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // keeps splash screen while we get authentication confirmation from DB
        splashScreen.setKeepOnScreenCondition {
            viewModel.uiState.value.shouldKeepSplashScreen()
        }

        enableEdgeToEdge()
        setContent {
            val appState = rememberAppState()

            PruebatecTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
                    val state = uiState.value

                    when (state) {
                        MainActivityUiState.Loading -> {} // no need to do anything as splash screen is kept while state is loading
                        is MainActivityUiState.Success -> ChallengeApp(
                            appState = appState,
                            modifier = Modifier.padding(innerPadding),
                            isAuthenticated = state.isAuthenticated
                        )
                    }
                }
            }
        }
    }
}
