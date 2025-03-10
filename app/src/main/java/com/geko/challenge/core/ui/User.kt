package com.geko.challenge.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geko.challenge.core.design.theme.PruebatecTheme
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
    PruebatecTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            User(
                {},
                UserUiState.Success(User("Ivanna")),
                null
            )
        }
    }
}
