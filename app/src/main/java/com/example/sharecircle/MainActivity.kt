package com.example.sharecircle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.sharecircle.ui.theme.ShareCircleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShareCircleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SplitwiseHomeLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitwiseHomeLayout(modifier: Modifier) {
    var showMenu by remember { mutableStateOf(false) }
    val tag = "SplitwiseHomeLayout"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("ShareCircle") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showMenu = !showMenu
                Log.d(tag, "showMenu: $showMenu")
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display expenses and balances here
            Text("No expenses yet!")
        }
    }
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false },
        modifier = Modifier.fillMaxWidth()

    ) {
        DropdownMenuItem(
            text = { Text("Add Expense") },
            onClick = { Log.d(tag, "Add Expense clicked") }
        )
        DropdownMenuItem(
            text = { Text("Back-payment") },
            onClick = { Log.d(tag, "Back-payment clicked") }
        )
        // Maybe more menu items for other actions
    }
}

@Preview(showBackground = true)
@Composable
fun SplitwiseHomeLayoutPreview() {
    SplitwiseHomeLayout(modifier = Modifier.fillMaxSize())
}