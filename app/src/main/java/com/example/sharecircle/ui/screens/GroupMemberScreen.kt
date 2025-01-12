package com.example.sharecircle.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import com.example.sharecircle.data.GroupMemberEntity
import com.example.sharecircle.data.UserEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupMembersScreen(
    viewModel: ShareCircleViewModel = viewModel(),
    navController: NavController,
    groupId: String
) {

    var showAddUserDialog by remember { mutableStateOf(false) }

    val uiState = viewModel.uiState.collectAsState().value
    val groupMembers = uiState.groupMembers


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add/Remove Group Members") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddUserDialog = true }) {
                Icon(Icons.Filled.Add, "Add Member")
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
            if (groupMembers.isEmpty()) {
                Text("No members in this group yet.")
            } else {
                LazyColumn {
                    items(groupMembers) { member ->
                        GroupMemberItem(
                            member = member,
                            onRemove = {
                                viewModel.removeUserFromGroup(member.id)
                            },
                            uiState = uiState
                        )
                    }
                }
            }
        }
    }
    if (showAddUserDialog) {
        AddUserToGroupDialog(
            onDismiss = { showAddUserDialog = false },
            onUserSelected = { user ->
                viewModel.addUserToGroup(groupId.toString(), user.id)
                showAddUserDialog = false
            },
            uiState = uiState
        )
    }
}

@Composable
fun GroupMemberItem(member: GroupMemberEntity, onRemove: () -> Unit, uiState: ShareCircleUIState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = uiState.users.find { it.id == member.userId }?.username ?: "Unknown")
        IconButton(onClick = onRemove) {
            Icon(Icons.Filled.Delete, "Remove Member")
        }
    }
}

@Composable
fun AddUserToGroupDialog(
    onDismiss: () -> Unit,
    onUserSelected: (UserEntity) -> Unit,
    uiState: ShareCircleUIState
) {
    val allUsers = uiState.users
    val groupMembers = uiState.groupMembers

    val availableUsers = allUsers.filter { user -> groupMembers.none { it.userId == user.id } }

    var selectedUserEntity by remember { mutableStateOf<UserEntity?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select User to Add") },
        text = {
            if (availableUsers.isEmpty()) {
                Text("No available users to add.")
            } else {
                Column {
                    availableUsers.forEach { user ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedUserEntity == user,
                                onClick = { selectedUserEntity = user }
                            )
                            Text(text = user.username)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedUserEntity?.let { onUserSelected(it) }
                },
                enabled = selectedUserEntity != null
            ) {
                Text("Add User")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
