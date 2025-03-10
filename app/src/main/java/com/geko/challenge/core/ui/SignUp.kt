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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geko.challenge.core.design.theme.PruebatecTheme

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
    onShowSnackbar: suspend (String) -> Unit,
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

            val buttonEnable = password.isNotEmpty() && email.isNotEmpty()
                    && !emailError && !passwordError
                    && firstName.isNotEmpty() && lastName.isNotEmpty()

            val keyboardController = LocalSoftwareKeyboardController.current

            Spacer(modifier = Modifier.height(32.dp))

            val requiredStar = buildAnnotatedString {
                withStyle(style = SpanStyle(Color.Red)) {
                    append("*")
                }
            }

            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                label = {
                    Text(
                        buildAnnotatedString { append("First Name ") }.plus(requiredStar)
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                label = {
                    Text(
                        buildAnnotatedString { append("Last Name ") }.plus(requiredStar)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it

                    emailError =
                        !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                },
                label = {
                    Text(
                        buildAnnotatedString { append("Email ") }.plus(requiredStar)
                    )
                },
                isError = emailError,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
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
                    password = it

                    passwordError = !password
                        .matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{6,}".toRegex())
                },
                label = {
                    Text(
                        buildAnnotatedString { append("Password ") }.plus(requiredStar)
                    )
                },
                isError = passwordError,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions(
                    onGo = {
                        keyboardController?.hide()
                        if (buttonEnable) {
                            onRegister(firstName, lastName, email, password)
                        }
                    }
                ),
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
//                    if () {
                        onRegister(firstName, lastName, email, password)
//                    }
                },
                enabled = buttonEnable,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    when(uiEvent) {
        is SignUpLogicUiEvent.onShowSnackBar ->  LaunchedEffect(uiEvent.effectToggle) {
            onShowSnackbar(uiEvent.message)
        }
        else -> {}
    }
}


sealed interface SignUpUiState {
    data object Loading : SignUpUiState

    data class Success(
        val isLoading: Boolean = false
    ): SignUpUiState
}

sealed interface SignUpLogicUiEvent {
    data class onShowSnackBar(
        val message: String,
        val effectToggle: Boolean?
    ): SignUpLogicUiEvent
}

@Preview(showBackground = true)
@Composable
private fun PreviewSignUpScreen() {
    PruebatecTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            SignUp(
                { _, _, _, _ -> },
                SignUpUiState.Success(),
                null,
                {}
            )
        }
    }
}
