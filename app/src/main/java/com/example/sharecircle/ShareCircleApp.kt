package com.example.sharecircle

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sharecircle.ui.screens.ExpenseCreateScreen
import com.example.sharecircle.ui.screens.ExpenseDetailsScreen
import com.example.sharecircle.ui.screens.HomeScreen
import com.example.sharecircle.ui.screens.GroupCreateScreen
import com.example.sharecircle.ui.screens.GroupDetailsScreen
import com.example.sharecircle.ui.screens.GroupMembersScreen
import com.example.sharecircle.ui.screens.PaymentCreateScreen
import com.example.sharecircle.ui.screens.PaymentDetailsScreen
import com.example.sharecircle.ui.screens.UserAddScreen

enum class ShareCircleScreen {
    Home,
    ExpenseCreate,
    PaymentCreate,
    GroupCreate,
    UserAdd,
    GroupMembers,
    ExpenseDetails,
    PaymentDetails,
    GroupDetails,
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
        composable(route = ShareCircleScreen.ExpenseCreate.name) {
            ExpenseCreateScreen(viewModel = viewModel, navController = navController)
        }
        composable(route = ShareCircleScreen.GroupCreate.name) {
            GroupCreateScreen(viewModel = viewModel, navController = navController)
        }
        composable(route = ShareCircleScreen.GroupDetails.name + "/{groupId}") { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId")
            GroupDetailsScreen(viewModel = viewModel, navController = navController, groupId = groupId.toString())
        }
        composable(route = ShareCircleScreen.GroupMembers.name + "/{groupId}") { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId")
            GroupMembersScreen(viewModel = viewModel, navController = navController, groupId = groupId.toString())
        }
        composable(route = ShareCircleScreen.UserAdd.name) {
            UserAddScreen(viewModel = viewModel, navController = navController)
        }
        composable(route = ShareCircleScreen.PaymentCreate.name) {
            PaymentCreateScreen(viewModel = viewModel, navController = navController)
        }
        composable(route = ShareCircleScreen.ExpenseDetails.name + "/{expenseId}") { backStackEntry ->
            val expenseId = backStackEntry.arguments?.getString("expenseId")
            ExpenseDetailsScreen(viewModel = viewModel, navController = navController, expenseId = expenseId.toString())
        }
        composable(route = ShareCircleScreen.PaymentDetails.name + "/{paymentId}") { backStackEntry ->
            val paymentId = backStackEntry.arguments?.getString("paymentId")
            PaymentDetailsScreen(viewModel = viewModel, navController = navController, paymentId = paymentId.toString())
        }
    }
}