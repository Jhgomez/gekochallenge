package com.geko.challenge.core.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ColumnScope.Login(
    onLoginClick: (String, String) -> Unit,
    onSignUpClick: () -> Unit,
    onShowSnackbar: suspend (String) -> Unit,
    uiState: LoginUiState,
    uiEvent: LoginLogicUiEvent?,
) {
    when (uiState) {
        LoginUiState.Loading -> {

        }

        is LoginUiState.Success -> {
            var email by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = { Text("Email") },
                isError = uiState.emailError,
                singleLine = true,
                supportingText = @Composable {
                    if (uiState.emailError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Enter valid email",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
//                passwordError = it.length < 6
                },
                label = { Text("Password") },
                isError = uiState.passwordError,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                supportingText = @Composable {
                    if (uiState.passwordError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Enter valid email",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onLoginClick(email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onSignUpClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign up")
            }

        }
    }

    when(uiEvent) {
        is LoginLogicUiEvent.ShowSnackBar ->  LaunchedEffect(uiEvent.effectToggle) {
            onShowSnackbar(uiEvent.message)
        }
        else -> {}
    }
}


sealed interface LoginUiState {
    data object Loading : LoginUiState

    data class Success(
        val emailError: Boolean,
        val passwordError: Boolean
    ) : LoginUiState
}

sealed interface LoginLogicUiEvent {
    data class ShowSnackBar(
        val message: String,
        val effectToggle: Boolean?
    ) : LoginLogicUiEvent

    data class NavigateToUserScreen(
        val userName: String
    ) : LoginLogicUiEvent
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen() {
    Column {
//        Login(
//            { _, _ -> },
//            {},
//            false,
//            true,
//            uiState
//        )
    }
}
