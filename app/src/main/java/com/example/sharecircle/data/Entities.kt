package com.example.sharecircle.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

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

@Entity(tableName = "payment")
data class PaymentEntity(
    @PrimaryKey val id: String,
    val amount: Double,
    val payerId: String,
    val payeeId: String,
    val groupId: String
)

@Entity(tableName = "expense")
data class ExpenseEntity(
    @PrimaryKey() val id: String,
    val amount: Double,
    val title: String,
    val payerId: String,
    val groupId: String
)

@Entity(tableName = "group_member",
    primaryKeys = ["userId", "groupId"],
    foreignKeys = [
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["userId"]),
        ForeignKey(entity = GroupEntity::class, parentColumns = ["id"], childColumns = ["groupId"])
    ])
data class GroupMemberEntity(
    val id: String,
    val userId: String,
    val groupId: String,
    val balance: Double
)

@Entity(tableName = "expense_detail",
        primaryKeys = ["expenseId", "payeeId"],
        foreignKeys = [
            ForeignKey(entity = ExpenseEntity::class, parentColumns = ["id"], childColumns = ["expenseId"], onDelete = ForeignKey.CASCADE),
            ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["payeeId"], onDelete = ForeignKey.CASCADE)
        ])
data class ExpenseDetailEntity(
    val id: String,
    val expenseId: String,
    val payeeId: String,
    val amount: Double
)


data class UserWithExpenses(
    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "payerId"
    )
    val playlists: List<ExpenseEntity>
)

data class UserPayerWithPayments(
    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "payerId"
    )
    val playlists: List<PaymentEntity>
)

data class UserPayeeWithPayments(
    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "payeeId"
    )
    val playlists: List<PaymentEntity>
)

data class GroupWithExpenses(
    @Embedded val groupEntity: GroupEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val expens: List<ExpenseEntity>
)

data class GroupWithPayments(
    @Embedded val groupEntity: GroupEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId"
    )
    val paymentEntities: List<PaymentEntity>
)

data class ExpenseWithDetails(
    @Embedded val expenseEntity: ExpenseEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "expenseId"
    )
    val details: List<ExpenseDetailEntity>
)

data class UserWithExpenseDetails(
    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "payeeId"
    )
    val expenseDetailEntities: List<ExpenseDetailEntity>
)