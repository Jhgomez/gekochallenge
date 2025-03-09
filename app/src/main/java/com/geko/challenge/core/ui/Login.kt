package com.geko.challenge.core.ui

import android.util.Patterns
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
            var emailError by rememberSaveable { mutableStateOf(false) }
            var passwordError by rememberSaveable { mutableStateOf(false) }

            OutlinedTextField(
                value = email,
                onValueChange = {
                    if (emailError) emailError = false
                    email = it
                },
                label = { Text("Email") },
                isError = emailError,
                singleLine = true,
                supportingText = @Composable {
                    if (emailError) {
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
                    if (passwordError) passwordError = false
                    password = it
                },
                label = { Text("Password") },
                isError = passwordError,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                supportingText = @Composable {
                    if (passwordError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Please enter your password",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    emailError =
                        !Patterns.EMAIL_ADDRESS.matcher(email).matches()

                    passwordError = password.isEmpty()

                    if (!emailError && !passwordError) onLoginClick(email, password)
                },
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
        val isLoading: Boolean = false
    ) : LoginUiState
}

sealed interface LoginLogicUiEvent {
    data class ShowSnackBar(
        val message: String,
        val effectToggle: Boolean?
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
