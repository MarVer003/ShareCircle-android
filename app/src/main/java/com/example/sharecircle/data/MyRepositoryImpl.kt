package com.example.sharecircle.data

class MyRepositoryImpl(
    private val userDAO: UserDAO,
    private val groupDAO: GroupDAO,
    private val expenseDAO: ExpenseDAO,
    private val paymentDAO: PaymentDAO,
    private val groupMemberDAO: GroupMemberDAO,
    private val expenseDetailDAO: ExpenseDetailDAO
) : MyRepository {
    override suspend fun getUsers(): List<UserEntity> {
        return userDAO.getUsers() ?: emptyList()
    }

    override suspend fun getUsersByGroupId(groupId: String): List<UserEntity> {
        return userDAO.getUsersByGroupId(groupId) ?: emptyList()
    }

    override suspend fun getUserById(id: String): UserEntity? {
        return userDAO.getUserById(id)
    }

    override suspend fun addUser(user: UserEntity) {
        userDAO.addUser(user)
    }

    override suspend fun updateUser(user: UserEntity) {
        userDAO.updateUser(user)
    }

    override suspend fun deleteUser(user: UserEntity) {
        userDAO.deleteUser(user)
    }

    override suspend fun getGroups(): List<GroupEntity> {
        return groupDAO.getGroups() ?: emptyList()
    }

    override suspend fun getGroupById(id: String): GroupEntity? {
        return groupDAO.getGroupById(id)
    }

    override suspend fun addGroup(group: GroupEntity) {
        groupDAO.addGroup(group)
    }

    override suspend fun updateGroup(group: GroupEntity) {
        groupDAO.updateGroup(group)
    }

    override suspend fun deleteGroup(group: GroupEntity) {
        groupDAO.deleteGroup(group)
    }

    override suspend fun getExpenses(): List<ExpenseEntity> {
        return expenseDAO.getExpenses() ?: emptyList()
    }

    override suspend fun getExpensesByGroupId(groupId: String): List<ExpenseEntity> {
        return expenseDAO.getExpensesByGroupId(groupId) ?: emptyList()
    }

    override suspend fun getExpenseById(id: String): ExpenseEntity? {
        return expenseDAO.getExpenseById(id)
    }

    override suspend fun addExpense(expense: ExpenseEntity) {
        expenseDAO.addExpense(expense)
    }

    override suspend fun updateExpense(expense: ExpenseEntity) {
        expenseDAO.updateExpense(expense)
    }

    override suspend fun deleteExpense(expense: ExpenseEntity) {
        expenseDAO.deleteExpense(expense)
    }

    override suspend fun getPayments(): List<PaymentEntity> {
        return paymentDAO.getPayments() ?: emptyList()
    }

    override suspend fun getPaymentsByGroupId(groupId: String): List<PaymentEntity> {
        return paymentDAO.getPaymentsByGroupId(groupId) ?: emptyList()
    }

    override suspend fun getPaymentById(id: String): PaymentEntity? {
        return paymentDAO.getPaymentById(id)
    }

    override suspend fun addPayment(payment: PaymentEntity) {
        paymentDAO.addPayment(payment)
    }

    override suspend fun updatePayment(payment: PaymentEntity) {
        paymentDAO.updatePayment(payment)
    }

    override suspend fun deletePayment(payment: PaymentEntity) {
        paymentDAO.deletePayment(payment)
    }

    override suspend fun getGroupMembers(): List<GroupMemberEntity> {
        return groupMemberDAO.getGroupMembers() ?: emptyList()
    }

    override suspend fun getGroupMembersByGroupId(groupId: String): List<GroupMemberEntity> {
        return groupMemberDAO.getGroupMembersByGroupId(groupId) ?: emptyList()
    }

    override suspend fun getGroupMemberById(id: String): GroupMemberEntity? {
        return groupMemberDAO.getGroupMemberById(id)
    }

    override suspend fun addGroupMember(groupMember: GroupMemberEntity) {
        groupMemberDAO.addGroupMember(groupMember)
    }

    override suspend fun updateGroupMember(groupMember: GroupMemberEntity) {
        groupMemberDAO.updateGroupMember(groupMember)
    }

    override suspend fun deleteGroupMember(groupMember: GroupMemberEntity) {
        groupMemberDAO.deleteGroupMember(groupMember)
    }

    override suspend fun getExpenseDetails(): List<ExpenseDetailEntity> {
        return expenseDetailDAO.getExpenseDetails() ?: emptyList()
    }

    override suspend fun getExpenseDetailsByGroupId(groupId: String): List<ExpenseDetailEntity> {
        return expenseDetailDAO.getExpenseDetailsByGroupId(groupId) ?: emptyList()
    }

    override suspend fun getExpenseDetailsByExpenseId(expenseId: String): List<ExpenseDetailEntity> {
        return expenseDetailDAO.getExpenseDetailsByExpenseId(expenseId) ?: emptyList()
    }

    override suspend fun addExpenseDetail(expenseDetail: ExpenseDetailEntity) {
        expenseDetailDAO.addExpenseDetails(expenseDetail)
    }

    override suspend fun updateExpenseDetail(expenseDetail: ExpenseDetailEntity) {
        expenseDetailDAO.updateExpenseDetails(expenseDetail)
    }

    override suspend fun updateExpenseDetails(expenseDetails: List<ExpenseDetailEntity>, newAmount: Double) {
        expenseDetails.forEach { detail ->
            expenseDetailDAO.updateExpenseDetails(detail.copy(amount = newAmount / expenseDetails.size))
        }
    }

    override suspend fun deleteExpenseDetail(expenseDetail: ExpenseDetailEntity) {
        expenseDetailDAO.deleteExpenseDetails(expenseDetail)
    }

    override suspend fun deleteExpenseDetailsByExpenseId(expenseId: String) {
        expenseDetailDAO.deleteExpenseDetailsByExpenseId(expenseId)
    }

    override fun closeDatabase() {
        MyDatabase.close()
    }
}