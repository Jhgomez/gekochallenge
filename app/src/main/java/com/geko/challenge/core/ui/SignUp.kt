package com.geko.challenge.core.ui

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

typealias registerInfo = (
    firtstName: String,
    lastName: String,
    email: String,
    password: String
) -> Unit

@Composable
fun ColumnScope.SignUp(
    onRegister: registerInfo,
    uiState: SignUpUiState,
    uiEvent: SignUpLogicUiEvent?,
) {
    when (uiState) {
        SignUpUiState.Loading -> {

        }

        is SignUpUiState.Success -> {
            var firstName by rememberSaveable { mutableStateOf("") }
            var lastName by rememberSaveable { mutableStateOf("") }
            var email by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }
            var emailError by rememberSaveable { mutableStateOf(false) }
            var passwordError by rememberSaveable { mutableStateOf(false) }

            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                label = { Text("First Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                label = { Text("Last Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    if (emailError) emailError = !emailError
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

            OutlinedTextField(
                value = password,
                onValueChange = {
                    if(passwordError) passwordError = !passwordError
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
                            text = "Password must have at least 1 lowercase, 1 uppercase, 1 diggit, "
                                    + "1 special character and be 6 characters logn",
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

                    passwordError = !password
                        .matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{6,}".toRegex())

                    if (!emailError && !passwordError) {
                        onRegister(firstName, lastName, email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }
        }
    }

    when(uiEvent) {
        null -> {}
        else -> {}
    }
}


sealed interface SignUpUiState {
    data object Loading : SignUpUiState

    data class Success(
        val isLoading: Boolean = false
    ): SignUpUiState
}

sealed interface SignUpLogicUiEvent

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpScreen() {
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
