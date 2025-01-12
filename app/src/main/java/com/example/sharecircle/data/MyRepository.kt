package com.example.sharecircle.data


interface MyRepository {
    suspend fun getUsers(): List<UserEntity>
    suspend fun getUsersByGroupId(groupId: String): List<UserEntity>
    suspend fun getUserById(id: String): UserEntity?
    suspend fun addUser(user: UserEntity)
    suspend fun updateUser(user: UserEntity)
    suspend fun deleteUser(user: UserEntity)

    suspend fun getGroups(): List<GroupEntity>
    suspend fun getGroupById(id: String): GroupEntity?
    suspend fun addGroup(group: GroupEntity)
    suspend fun updateGroup(group: GroupEntity)
    suspend fun deleteGroup(group: GroupEntity)

    suspend fun getExpenses(): List<ExpenseEntity>
    suspend fun getExpensesByGroupId(groupId: String): List<ExpenseEntity>
    suspend fun getExpenseById(id: String): ExpenseEntity?
    suspend fun addExpense(expense: ExpenseEntity)
    suspend fun updateExpense(expense: ExpenseEntity)
    suspend fun deleteExpense(expense: ExpenseEntity)

    suspend fun getPayments(): List<PaymentEntity>
    suspend fun getPaymentsByGroupId(groupId: String): List<PaymentEntity>
    suspend fun getPaymentById(id: String): PaymentEntity?
    suspend fun addPayment(payment: PaymentEntity)
    suspend fun updatePayment(payment: PaymentEntity)
    suspend fun deletePayment(payment: PaymentEntity)

    suspend fun getGroupMembers(): List<GroupMemberEntity>
    suspend fun getGroupMembersByGroupId(groupId: String): List<GroupMemberEntity>
    suspend fun getGroupMemberById(id: String): GroupMemberEntity?
    suspend fun addGroupMember(groupMember: GroupMemberEntity)
    suspend fun updateGroupMember(groupMember: GroupMemberEntity)
    suspend fun deleteGroupMember(groupMember: GroupMemberEntity)

    suspend fun getExpenseDetails(): List<ExpenseDetailEntity>
    suspend fun getExpenseDetailsByGroupId(groupId: String): List<ExpenseDetailEntity>
    suspend fun getExpenseDetailsByExpenseId(expenseId: String): List<ExpenseDetailEntity>
    suspend fun addExpenseDetail(expenseDetail: ExpenseDetailEntity)
    suspend fun updateExpenseDetail(expenseDetail: ExpenseDetailEntity)
    suspend fun updateExpenseDetails(expenseDetails: List<ExpenseDetailEntity>, newAmount: Double)
    suspend fun deleteExpenseDetail(expenseDetail: ExpenseDetailEntity)
    suspend fun deleteExpenseDetailsByExpenseId(expenseId: String)

}