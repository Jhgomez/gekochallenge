package com.geko.challenge.core.ui

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geko.challenge.core.design.theme.PruebatecTheme

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

            val keyboardController = LocalSoftwareKeyboardController.current

            OutlinedTextField(
                value = email,
                onValueChange = {
                    if (emailError) emailError = false
                    email = it
                },
                label = { Text("Email") },
                isError = emailError,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next)
                ,
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions(
                    onGo = {
                        keyboardController?.hide()

                        emailError =
                            !Patterns.EMAIL_ADDRESS.matcher(email).matches()

                        passwordError = password.isEmpty()

                        if (!emailError && !passwordError) onLoginClick(email, password)
                    }
                ),
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
    PruebatecTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Login(
                { _, _ -> },
                {},
                {},
                LoginUiState.Success(),
                null
            )
        }
    }
}
