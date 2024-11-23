package com.example.sharecircle

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sharecircle.ui.screens.HomeScreen
import com.example.sharecircle.ui.viewmodel.ShareCircleViewModel

enum class ShareCircleScreen {
    Home,
    Expense,
    BackPayement
}

@Composable
fun ShareCircleApp(viewModel: ShareCircleViewModel = viewModel(),
                   navController: NavHostController = rememberNavController()) {

    NavHost(navController = navController,
        startDestination = ShareCircleScreen.Home.name
    ) {
        composable(route = ShareCircleScreen.Home.name) {
            HomeScreen(viewModel = viewModel, navController = navController)
        }
/*        composable(route = ShareCircleScreen.Expense.name) {
            ExpenseScreen(viewModel = viewModel, navController = navController)
        }
        composable(route = ShareCircleScreen.BackPayement.name) {
            BackPayementScreen(viewModel = viewModel, navController = navController)
        }*/
    }
}