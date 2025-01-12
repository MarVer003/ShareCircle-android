package com.example.sharecircle.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sharecircle.ShareCircleViewModel
import com.example.sharecircle.roundTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterScreen(viewModel: ShareCircleViewModel = viewModel(), navController: NavController) {
    val uiState = viewModel.uiState.collectAsState().value

    var amount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("") }
    var toCurrency by remember { mutableStateOf("") }
    var fromCurrencyExpanded by remember { mutableStateOf(false) }
    var toCurrencyExpanded by remember { mutableStateOf(false) }
    val currencies = uiState.exchangeRates.keys.toList()
    var fromCurrencySearchText by remember { mutableStateOf("") }
    var toCurrencySearchText by remember { mutableStateOf("") }

    val filteredFromCurrencies = currencies.filter { it.contains(fromCurrencySearchText, ignoreCase = true) }
    val filteredToCurrencies = currencies.filter { it.contains(toCurrencySearchText, ignoreCase = true) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Currency Converter") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // From Currency Dropdown with Search
            Box {
                OutlinedTextField(
                    value = fromCurrency,
                    onValueChange = { },
                    label = { Text("From Currency") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { fromCurrencyExpanded = !fromCurrencyExpanded }) {
                            Icon(Icons.Filled.ArrowDropDown, "Dropdown")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = fromCurrencyExpanded,
                    onDismissRequest = { fromCurrencyExpanded = false }
                ) {
                    OutlinedTextField(
                        value = fromCurrencySearchText,
                        onValueChange = { fromCurrencySearchText = it },
                        label = { Text("Search Currency") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )
                    filteredFromCurrencies.forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(currency) },
                            onClick = {
                                fromCurrency = currency
                                fromCurrencyExpanded = false
                                fromCurrencySearchText = ""
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // To Currency Dropdown with Search
            Box {
                OutlinedTextField(
                    value = toCurrency,
                    onValueChange = { },
                    label = { Text("To Currency") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { toCurrencyExpanded = !toCurrencyExpanded }) {
                            Icon(Icons.Filled.ArrowDropDown, "Dropdown")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = toCurrencyExpanded,
                    onDismissRequest = { toCurrencyExpanded = false }
                ) {
                    OutlinedTextField(
                        value = toCurrencySearchText,
                        onValueChange = { toCurrencySearchText = it },
                        label = { Text("Search Currency") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )
                    filteredToCurrencies.forEach { currency ->
                        DropdownMenuItem(
                            text = { Text(currency) },
                            onClick = {
                                toCurrency = currency
                                toCurrencyExpanded = false
                                toCurrencySearchText = ""
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.convertCurrency(amount, fromCurrency, toCurrency)
            }) {
                Text("Convert")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text("Converted Amount: ${uiState.convertionResult?.roundTo() ?: ""}")
        }
    }
}
