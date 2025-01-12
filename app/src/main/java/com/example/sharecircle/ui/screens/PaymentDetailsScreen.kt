package com.example.sharecircle.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sharecircle.ShareCircleScreen
import com.example.sharecircle.ShareCircleViewModel
import com.example.sharecircle.formatAsCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentDetailsScreen(
    viewModel: ShareCircleViewModel = viewModel(),
    navController: NavController,
    paymentId: String
) {
    val uiState = viewModel.uiState.collectAsState().value
    val payment = uiState.paymentInView
    val users = uiState.users
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    if (payment == null) return
    Scaffold(
        topBar = {
            TopAppBar (
                title = { Text("Payment Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (showDeleteConfirmation) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmation = false },
                title = { Text("Confirm Delete") },
                text = { Text("Are you sure you want to delete this payment? This action cannot be undone.") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.deletePayment(paymentId)
                        showDeleteConfirmation = false
                        navController.popBackStack()
                    }) {
                        Text("Delete", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteConfirmation = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Payment Details
            Text(
                text = "Payment Details",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Payer: ${users.find { it.id == payment.payerId }?.username ?: "Unknown"}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Payee: ${users.find { it.id == payment.payeeId }?.username ?: "Unknown"}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Amount: ${payment.amount.formatAsCurrency()}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons for Editing and Deleting
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate(ShareCircleScreen.PaymentCreate.name) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit Payment")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Edit")
                }
                Button(
                    onClick = { showDeleteConfirmation = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete Payment")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delete")
                }
            }
        }
    }
}
