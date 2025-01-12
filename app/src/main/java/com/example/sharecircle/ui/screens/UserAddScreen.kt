package com.example.sharecircle.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sharecircle.ShareCircleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAddScreen(viewModel: ShareCircleViewModel = viewModel(), navController: NavController) {

    val uiState = viewModel.uiState.collectAsState().value

    var username by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf(false) }
    var usernameExistsError by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                usernameError = false
                usernameExistsError = false
            },
            label = { Text("Username") },
            isError = usernameError || usernameExistsError
        )
        if (usernameError) {
            Text(
                text = "Please enter a username",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }
        if (usernameExistsError) {
            Text(
                text = "Username already exists",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (username.isEmpty()) {
                usernameError = true
            } else if (uiState.users.any { it.username == username }) {
                usernameExistsError = true
            }
            else {
                viewModel.addUser(username)
                navController.popBackStack()
            }
        }) {
            Text("Add User")
        }
    }
}