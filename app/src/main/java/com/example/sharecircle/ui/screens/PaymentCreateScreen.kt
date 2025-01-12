package com.example.sharecircle.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sharecircle.ShareCircleViewModel
import com.example.sharecircle.data.UserEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentCreateScreen(
    viewModel: ShareCircleViewModel = viewModel(),
    navController: NavController
) {

    val uiState = viewModel.uiState.collectAsState().value
    val payment = uiState.paymentInView
    val valueAmount = payment?.amount ?: ""

    var amount by remember { mutableStateOf(valueAmount.toString()) }

    var expandedPayer by remember { mutableStateOf(false) }
    var selectedPayer by remember { mutableStateOf<UserEntity?>(
        payment?.let { uiState.users.find { it.id == payment.payerId } }
    ) }
    var expandedPayee by remember { mutableStateOf(false) }
    var selectedPayee by remember { mutableStateOf<UserEntity?>(
        payment?.let { uiState.users.find { it.id == payment.payeeId } }
    ) }

    var payerError by remember { mutableStateOf(false) }
    var payeeError by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = amount,
            onValueChange = {
                amount = it
                amountError = false
            },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            isError = amountError
        )
        if (amountError) {
            Text(
                text = "Amount must be greater than 0",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        ExposedDropdownMenuBox(
            expanded = expandedPayer,
            onExpandedChange = { expandedPayer = it }
        ) {
            OutlinedTextField(
                value = selectedPayer?.username ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Payer") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPayer) },
                modifier = Modifier.menuAnchor(),
                isError = payerError
            )
            ExposedDropdownMenu(
                expanded = expandedPayer,
                onDismissRequest = { expandedPayer = false }
            ) {
                uiState.groupMembers.forEach { payer ->
                    DropdownMenuItem(
                        text = { Text(text = uiState.users.find { it.id == payer.userId }?.username ?: "Unknown") },
                        onClick = {
                            selectedPayer = uiState.users.find { it.id == payer.userId }
                            expandedPayer = false
                            payerError = false
                        }
                    )
                }
            }
        }
        if (payerError) {
            Text(
                text = "Please select a payer",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        // Payee Dropdown
        ExposedDropdownMenuBox(
            expanded = expandedPayee,
            onExpandedChange = { expandedPayee = it }
        ) {
            OutlinedTextField(
                value = selectedPayee?.username ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Payee") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPayee) },
                modifier = Modifier.menuAnchor(),
                isError = payeeError
            )
            ExposedDropdownMenu(
                expanded = expandedPayee,
                onDismissRequest = { expandedPayee = false }
            ) {
                uiState.groupMembers.forEach { payee ->
                    DropdownMenuItem(
                        text = { Text(text = uiState.users.find { it.id == payee.userId }?.username ?: "Unknown") },
                        onClick = {
                            selectedPayee = uiState.users.find { it.id == payee.userId }
                            expandedPayee = false
                            payeeError = false
                        }
                    )
                }
            }
        }
        if (payeeError) {
            Text(
                text = "Please select a payee",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val amountValue = amount.toDoubleOrNull()
            if (selectedPayer == null) {
                payerError = true
            } else if (selectedPayee == null) {
                payeeError = true
            } else if (amountValue == null || amountValue <= 0.0) {
                amountError = true
            } else {
                if (payment == null) {
                    viewModel.addPayment(amountValue, selectedPayer?.id.toString(), selectedPayee?.id.toString())
                }
                else {
                    viewModel.updatePayment(payment.id, amountValue, selectedPayer?.id.toString(), selectedPayee?.id.toString())
                }
                navController.popBackStack()
            }
        }) {
            val text = if (payment == null) "Create Payment" else "Update Payment"
            Text(text)
        }
    }
}