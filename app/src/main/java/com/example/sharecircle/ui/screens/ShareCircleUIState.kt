package com.example.sharecircle.ui.screens

import com.example.sharecircle.data.ExpenseDetailEntity
import com.example.sharecircle.data.ExpenseEntity
import com.example.sharecircle.data.GroupEntity
import com.example.sharecircle.data.GroupMemberEntity
import com.example.sharecircle.data.PaymentEntity
import com.example.sharecircle.data.UserEntity

data class ShareCircleUIState (
    val users: List<UserEntity> = emptyList(),
    val groups: List<GroupEntity> = emptyList(),
    val groupMembers: List<GroupMemberEntity> = emptyList(),
    val expensesInGroup: List<ExpenseEntity> = emptyList(),
    val paymentsInGroup: List<PaymentEntity> = emptyList(),
    val expenseDetails: List<ExpenseDetailEntity> = emptyList(),
    val paymentDetails: PaymentEntity = PaymentEntity("", 0.0, "", "", ""),
    val groupInView: GroupEntity? = null,
    val expenseInView: ExpenseEntity? = null,
    val paymentInView: PaymentEntity? = null

)

