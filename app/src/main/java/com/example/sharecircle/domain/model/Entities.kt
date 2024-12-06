package com.example.sharecircle.domain.model

import java.time.Instant

data class User(
    val id: Int,
    val username: String
)

data class Group(
    val id: Int,
    val name: String,
    val members: List<User>,
    val expenses: List<Expense>,
    val backPayements: List<BackPayement>
)

data class BackPayement(
    val id: Int,
    val amount: Double,
    val date: Instant,
    val payer: User,
    val payee: User,
    val group: Group
)

data class Expense(
    val id: Int,
    val amount: Double,
    val title: String,
    val date: Instant,
    val payer: User,
    val group: Group
)

data class ExpenseDetails(
    val id: Int,
    val expense: Expense,
    val amount: Double,
    val payee: User
)
