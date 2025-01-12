package com.example.sharecircle.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sharecircle.ShareCircleScreen
import com.example.sharecircle.ShareCircleViewModel
import com.example.sharecircle.data.GroupEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: ShareCircleViewModel = viewModel(), navController: NavController) {

    val uiState = viewModel.uiState.collectAsState().value

    var expandedMenu by remember { mutableStateOf(false) }


    Scaffold(
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
                        text = { Text("Add Group") },
                        onClick = {
                            navController.navigate(ShareCircleScreen.GroupCreate.name)
                            expandedMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Add User") },
                        onClick = {
                            navController.navigate(ShareCircleScreen.UserAdd.name)
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
            Text(
                text = "Available Groups",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (uiState.groups.isEmpty()) {
                Text(
                    text = "No groups yet. Click the + button to add a new group.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(uiState.groups) { group ->
                        GroupItem(groupEntity = group, navController = navController, viewModel)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupItem(groupEntity: GroupEntity, navController: NavController, viewModel: ShareCircleViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = {
            viewModel.groupInView(groupEntity)
            navController.navigate(ShareCircleScreen.GroupDetails.name + "/${groupEntity.id}")
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = groupEntity.name, style = MaterialTheme.typography.titleMedium)
        }
    }
}