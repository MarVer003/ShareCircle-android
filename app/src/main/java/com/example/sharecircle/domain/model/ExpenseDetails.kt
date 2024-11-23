package com.example.sharecircle.domain.model

data class ExpenseDetails(
    val id: Int,
    val expense: Expense,
    val amount: Double,
    val payee: User
)