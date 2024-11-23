package com.example.sharecircle.domain.model

import java.util.Date

data class BackPayement(
    val id: Int,
    val amount: Double,
    val date: Date,
    val payer: User,
    val payee: User,
    val group: Group
)