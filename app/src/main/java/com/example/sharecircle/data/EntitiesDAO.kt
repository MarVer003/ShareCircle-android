package com.example.sharecircle.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface UserDAO {
    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<UserEntity>?

    @Query("SELECT u.* FROM group_member gm " +
            "INNER JOIN `group` g ON gm.groupId = g.id " +
            "INNER JOIN user u ON gm.userId = u.id " +
            "WHERE g.id = :groupId")
    suspend fun getUsersByGroupId(groupId: String): List<UserEntity>?

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: String): UserEntity?

    @Insert
    suspend fun addUser(userEntity: UserEntity)

    @Update
    suspend fun updateUser(userEntity: UserEntity)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)
}

@Dao
interface GroupDAO {
    @Query("SELECT * FROM 'group'")
    suspend fun getGroups(): List<GroupEntity>?

    @Query("SELECT * FROM 'group' WHERE id = :id")
    suspend fun getGroupById(id: String): GroupEntity?

    @Insert
    suspend fun addGroup(groupEntity: GroupEntity)

    @Update
    suspend fun updateGroup(groupEntity: GroupEntity)

    @Delete
    suspend fun deleteGroup(groupEntity: GroupEntity)
}

@Dao
interface ExpenseDAO {
    @Query("SELECT * FROM expense")
    suspend fun getExpenses(): List<ExpenseEntity>?

    @Query("SELECT * FROM expense WHERE groupId = :groupId")
    suspend fun getExpensesByGroupId(groupId: String): List<ExpenseEntity>?

    @Query("SELECT * FROM expense WHERE id = :id")
    suspend fun getExpenseById(id: String): ExpenseEntity?

    @Insert
    suspend fun addExpense(expenseEntity: ExpenseEntity)

    @Update
    suspend fun updateExpense(expenseEntity: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expenseEntity: ExpenseEntity)
}

@Dao
interface PaymentDAO {
    @Query("SELECT * FROM payment")
    suspend fun getPayments(): List<PaymentEntity>?

    @Query("SELECT * FROM payment WHERE groupId = :groupId")
    suspend fun getPaymentsByGroupId(groupId: String): List<PaymentEntity>?

    @Query("SELECT * FROM payment WHERE id = :id")
    suspend fun getPaymentById(id: String): PaymentEntity?

    @Insert
    suspend fun addPayment(paymentEntity: PaymentEntity)

    @Update
    suspend fun updatePayment(paymentEntity: PaymentEntity)

    @Delete
    suspend fun deletePayment(paymentEntity: PaymentEntity)
}

@Dao
interface GroupMemberDAO {
    @Query("SELECT * FROM group_member")
    suspend fun getGroupMembers(): List<GroupMemberEntity>?

    @Query("SELECT * FROM group_member WHERE groupId = :groupId")
    suspend fun getGroupMembersByGroupId(groupId: String): List<GroupMemberEntity>?

    @Query("SELECT * FROM group_member WHERE id = :id")
    suspend fun getGroupMemberById(id: String): GroupMemberEntity?

    @Insert
    suspend fun addGroupMember(groupMemberEntity: GroupMemberEntity)

    @Update
    suspend fun updateGroupMember(groupMemberEntity: GroupMemberEntity)

    @Delete
    suspend fun deleteGroupMember(groupMemberEntity: GroupMemberEntity)

}

@Dao
interface ExpenseDetailDAO {
    @Query("SELECT * FROM expense_detail")
    suspend fun getExpenseDetails(): List<ExpenseDetailEntity>?

    @Query("SELECT ed.* FROM expense_detail ed " +
            "INNER JOIN expense e ON ed.expenseId = e.id " +
            "WHERE e.groupId = :groupId")
    suspend fun getExpenseDetailsByGroupId(groupId: String): List<ExpenseDetailEntity>?

    @Query("SELECT * FROM expense_detail WHERE expenseId = :expenseId")
    suspend fun getExpenseDetailsByExpenseId(expenseId: String): List<ExpenseDetailEntity>?

    @Insert
    suspend fun addExpenseDetails(expenseDetailEntity: ExpenseDetailEntity)

    @Update
    suspend fun updateExpenseDetails(expenseDetailEntity: ExpenseDetailEntity)

    @Delete
    suspend fun deleteExpenseDetails(expenseDetailEntity: ExpenseDetailEntity)

    @Query("DELETE FROM expense_detail WHERE expenseId = :expenseId")
    suspend fun deleteExpenseDetailsByExpenseId(expenseId: String)
}
