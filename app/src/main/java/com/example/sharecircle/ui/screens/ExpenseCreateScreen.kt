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
fun ExpenseCreateScreen(
    viewModel: ShareCircleViewModel = viewModel(),
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsState().value
    val expense = uiState.expenseInView
    val valueAmount = expense?.amount ?: ""

    var amount by remember { mutableStateOf(valueAmount.toString()) }
    var title by remember { mutableStateOf(expense?.title ?: "") }

    var expandedPayer by remember { mutableStateOf(false) }
    var selectedPayer by remember { mutableStateOf<UserEntity?>(
        expense?.let { uiState.users.find { it.id == expense.payerId } }) }

    var payerError by remember { mutableStateOf(false) }
    var titleError by remember { mutableStateOf(false) }
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
            value = title,
            onValueChange = {
                title = it
                titleError = false // Clear error when title changes
            },
            label = { Text("Title") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            isError = titleError
        )
        if (titleError) {
            Text(
                text = "Enter a title",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
            )
        }

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
                text = "Enter an amount greater than 0",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
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
                text = "Select a payer",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val amountValue = amount.toDoubleOrNull()
            if (title.isEmpty()) {
                titleError = true
            }
            else if (amountValue == null || amountValue <= 0.0) {
                amountError = true
            }
            else if (selectedPayer == null) {
                payerError = true
            }
            else {
                if (expense == null) {
                    viewModel.addExpense(amountValue, title, selectedPayer?.id!!)
                }
                else {
                    viewModel.updateExpense(expense.id, amountValue, title, selectedPayer?.id!!)
                }
                navController.popBackStack()
            }
        }) {
            val text = if (expense == null) "Create Expense" else "Update Expense"
            Text(text)
        }
    }
}