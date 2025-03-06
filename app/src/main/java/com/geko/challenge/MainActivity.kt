package com.geko.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.geko.challenge.core.data.repository.AuthenticationRepository
import com.geko.challenge.core.design.theme.PruebatecTheme
import com.geko.challenge.ui.ChallengeApp
import com.geko.challenge.ui.rememberAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authenticationRepository: AuthenticationRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberAppState(
                authenticationRepository = authenticationRepository
            )

            PruebatecTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChallengeApp(
                        appState = appState,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
