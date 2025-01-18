package com.example.sharecircle.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: String,
    val username: String
)

@Entity(tableName = "group")
data class GroupEntity(
    @PrimaryKey val id: String,
    val name: String,
)

@Entity(tableName = "payment",
    foreignKeys = [
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["payerId"]),
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["payeeId"]),
        ForeignKey(entity = GroupEntity::class, parentColumns = ["id"], childColumns = ["groupId"])
    ])
data class PaymentEntity(
    @PrimaryKey val id: String,
    val amount: Double,
    val payerId: String,
    val payeeId: String,
    val groupId: String
)

@Entity(tableName = "expense",
    foreignKeys = [
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["payerId"]),
        ForeignKey(entity = GroupEntity::class, parentColumns = ["id"], childColumns = ["groupId"])
    ])
data class ExpenseEntity(
    @PrimaryKey val id: String,
    val amount: Double,
    val title: String,
    val payerId: String,
    val groupId: String
)

@Entity(tableName = "group_member",
    foreignKeys = [
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["userId"]),
        ForeignKey(entity = GroupEntity::class, parentColumns = ["id"], childColumns = ["groupId"])
    ])
data class GroupMemberEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val groupId: String,
    val balance: Double
)

@Entity(tableName = "expense_detail",
        foreignKeys = [
            ForeignKey(entity = ExpenseEntity::class, parentColumns = ["id"], childColumns = ["expenseId"], onDelete = ForeignKey.CASCADE),
            ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["payeeId"], onDelete = ForeignKey.CASCADE)
        ])
data class ExpenseDetailEntity(
    @PrimaryKey val id: String,
    val expenseId: String,
    val payeeId: String,
    val amount: Double
)