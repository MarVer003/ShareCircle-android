package com.example.sharecircle.domain.model

import java.util.Date

data class Expense(
    val id: Int,
    val amount: Double,
    val title: String,
    val date: Date,
    val payer: User,
    val group: Group
)