package com.geko.challenge.core.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.geko.challenge.core.model.User


@Composable
fun ColumnScope.User(
    onLogout: () -> Unit,
    uiState: UserUiState,
    uiEvent: UserLogicUiEvent?,
) {
    when (uiState) {
        UserUiState.Loading -> {

        }

        is UserUiState.Success -> {

            Text(
                text = "Hola ${uiState.user.name}, bienvenido",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    when(uiEvent) {
        null -> {}
        else -> {}
    }
}


sealed interface UserUiState {
    data object Loading : UserUiState

    data class Success(
        val user: User
    ) : UserUiState
}

sealed interface UserLogicUiEvent

@Preview(showBackground = true)
@Composable
private fun PreviewUserScreen() {
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
