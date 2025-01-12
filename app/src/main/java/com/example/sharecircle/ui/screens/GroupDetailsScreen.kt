package com.example.sharecircle.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sharecircle.ShareCircleScreen
import com.example.sharecircle.ShareCircleViewModel
import com.example.sharecircle.data.ExpenseEntity
import com.example.sharecircle.data.PaymentEntity
import com.example.sharecircle.formatAsCurrency
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun GroupDetailsScreen(
    viewModel: ShareCircleViewModel = viewModel(),
    navController: NavController,
    groupId: String
) {
    val uiState = viewModel.uiState.collectAsState().value

    val pagerState = rememberPagerState()
    var expandedMenu by remember { mutableStateOf(false) }
    var expandedDotsMenu by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = uiState.groupInView?.name ?: "Group Details") },
                actions = {
                    IconButton(onClick = { expandedDotsMenu = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "More Options")
                    }
                    DropdownMenu(
                        expanded = expandedDotsMenu,
                        onDismissRequest = { expandedDotsMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Group Members") },
                            onClick = {
                                navController.navigate(ShareCircleScreen.GroupMembers.name + "/$groupId")
                                expandedDotsMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Currency Converter") },
                            onClick = {
                                viewModel.initCurrencyConverterRates()
                                navController.navigate(ShareCircleScreen.CurrencyConverter.name)
                                expandedDotsMenu = false
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Box {
                FloatingActionButton(onClick = { expandedMenu = true }) {
                    Icon(Icons.Filled.Add, "Add")
                }
                DropdownMenu(
                    expanded = expandedMenu,
                    onDismissRequest = { expandedMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Add Expense") },
                        onClick = {
                            viewModel.resetExpenseInView()
                            navController.navigate(ShareCircleScreen.ExpenseCreate.name)
                            expandedMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Add Payment") },
                        onClick = {
                            viewModel.resetPaymentInView()
                            navController.navigate(ShareCircleScreen.PaymentCreate.name)
                            expandedMenu = false
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    },
                    text = { Text("Expenses") }
                )
                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    },
                    text = { Text("Payments") }
                )
                Tab(
                    selected = pagerState.currentPage == 2,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    },
                    text = { Text("Balances") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalPager(count = 3, state = pagerState) { page ->
                when (page) {
                    0 -> ExpensesTabContent(navController = navController, uiState, viewModel)
                    1 -> PaymentsTabContent(navController = navController, uiState, viewModel)
                    2 -> BalancesTabContent(uiState)
                }
            }
        }
    }
}


@Composable
fun BalancesTabContent(uiState: ShareCircleUIState) {
    val members = uiState.groupMembers
    if (members.isEmpty()) {
        Text(
            text = "No balances yet.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
    else {
        Column(modifier = Modifier.fillMaxWidth()) {
            members.forEach { (_, userId, _, balance) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = uiState.users.find { it.id == userId }?.username ?: "Unknown", style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = balance.formatAsCurrency(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (balance >= 0) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExpensesTabContent(navController: NavController, uiState: ShareCircleUIState, viewModel: ShareCircleViewModel) {
    if (uiState.expensesInGroup.isEmpty()) {
        Text(
            text = "No expenses yet.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        Column {
            uiState.expensesInGroup.forEach { expense ->
                ExpenseItem(expenseEntity = expense, navController = navController, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun PaymentsTabContent(navController: NavController, uiState: ShareCircleUIState, viewModel: ShareCircleViewModel) {
    if (uiState.paymentsInGroup.isEmpty()) {
        Text(
            text = "No payments yet.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        Column {
            uiState.paymentsInGroup.forEach { payment ->
                PaymentItem(paymentEntity = payment, navController = navController, uiState, viewModel)
            }
        }
    }
}

@Composable
fun ExpenseItem(expenseEntity: ExpenseEntity, navController: NavController, viewModel: ShareCircleViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                viewModel.expenseInView(expenseEntity)
                navController.navigate(ShareCircleScreen.ExpenseDetails.name + "/${expenseEntity.id}") },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = expenseEntity.title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Amount: ${expenseEntity.amount.formatAsCurrency()}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun PaymentItem(paymentEntity: PaymentEntity, navController: NavController, uiState: ShareCircleUIState, viewModel: ShareCircleViewModel) {
    val payer = uiState.users.find { it.id == paymentEntity.payerId }
    val payee = uiState.users.find { it.id == paymentEntity.payeeId }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                viewModel.paymentInView(paymentEntity)
                navController.navigate(ShareCircleScreen.PaymentDetails.name + "/${paymentEntity.id}") },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "From: ${payer?.username ?: "Unknown"}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "To: ${payee?.username ?: "Unknown"}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Amount: ${paymentEntity.amount.formatAsCurrency()}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
