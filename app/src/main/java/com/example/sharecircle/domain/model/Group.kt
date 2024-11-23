package com.example.sharecircle.domain.model

data class Group(
    val id: Int,
    val name: String,
    val members: List<User>,
    val expenses: List<Expense>,
    val backPayements: List<BackPayement>
)