package com.example.sharecircle

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharecircle.data.PaymentEntity
import com.example.sharecircle.data.ExpenseEntity
import com.example.sharecircle.data.ExpenseDetailEntity
import com.example.sharecircle.data.GroupEntity
import com.example.sharecircle.data.GroupMemberEntity
import com.example.sharecircle.data.MyRepository
import com.example.sharecircle.data.UserEntity
import com.example.sharecircle.network.Api
import com.example.sharecircle.ui.screens.ShareCircleUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID


class ShareCircleViewModel(private val repository: MyRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ShareCircleUIState())
    val uiState: StateFlow<ShareCircleUIState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            apiUsage()
            val users = repository.getUsers()
            val groups = repository.getGroups()
            _uiState.value = ShareCircleUIState(users = users, groups = groups)
        }
    }

    fun addUser(username: String) {
        viewModelScope.launch {
            val userEntity = UserEntity(id = UUID.randomUUID().toString(), username = username)
            repository.addUser(userEntity)
            _uiState.update { state -> state.copy(users = state.users + userEntity) }
        }
    }

    fun addGroup(groupName: String) {
        viewModelScope.launch {
            val groupEntity = GroupEntity(id = UUID.randomUUID().toString(), name = groupName)
            repository.addGroup(groupEntity)
            _uiState.update { state -> state.copy(groups = state.groups + groupEntity) }
        }
    }

    fun addExpense(amount: Double, title: String, payerId: String) {
        viewModelScope.launch {
            val expenseEntity = ExpenseEntity(
                id = UUID.randomUUID().toString(),
                amount = amount,
                title = title,
                payerId = payerId,
                groupId = _uiState.value.groupInView?.id.toString())
            repository.addExpense(expenseEntity)
            val members = repository.getUsersByGroupId(expenseEntity.groupId)
            members.forEach { member ->
                val newExpenseDetailEntity = ExpenseDetailEntity(
                    id = UUID.randomUUID().toString(),
                    expenseId = expenseEntity.id,
                    payeeId = member.id,
                    amount = expenseEntity.amount / members.size
                )
                repository.addExpenseDetail(newExpenseDetailEntity)
                _uiState.update { state -> state.copy(expenseDetails = state.expenseDetails + newExpenseDetailEntity) }
            }
            _uiState.update { state -> state.copy(expensesInGroup = state.expensesInGroup + expenseEntity) }
            calculateBalances()
        }
    }

    fun addPayment(amount: Double, payerId: String, payeeId: String) {
        viewModelScope.launch {
            val paymentEntity = PaymentEntity(
                id = UUID.randomUUID().toString(),
                amount = amount,
                payerId = payerId,
                payeeId = payeeId,
                groupId = _uiState.value.groupInView?.id.toString())
            repository.addPayment(paymentEntity)
            _uiState.update { state -> state.copy(paymentsInGroup = state.paymentsInGroup + paymentEntity) }
            calculateBalances()
        }
    }

    fun deleteExpense(expenseId : String) {
        viewModelScope.launch {
            val expense = repository.getExpenseById(expenseId)
            repository.deleteExpenseDetailsByExpenseId(expenseId)
            repository.deleteExpense(expense!!)
            _uiState.update { state -> state.copy(expensesInGroup = repository.getExpensesByGroupId(_uiState.value.groupInView?.id!!))}
            _uiState.update { state -> state.copy(expenseDetails = repository.getExpenseDetailsByGroupId(_uiState.value.groupInView?.id!!))}
            calculateBalances()
        }
    }

    fun deletePayment(paymentId : String) {
        viewModelScope.launch {
            val payment = repository.getPaymentById(paymentId)
            repository.deletePayment(payment!!)
            _uiState.update { state -> state.copy(paymentsInGroup = repository.getPaymentsByGroupId(payment.groupId)) }
            calculateBalances()
        }
    }

    fun calculateBalances() {
        viewModelScope.launch {
            val groupId = _uiState.value.groupInView?.id.toString()
            val expensesInGroup = repository.getExpensesByGroupId(groupId)
            val paymentsInGroup = repository.getPaymentsByGroupId(groupId)
            val expenseDetails = repository.getExpenseDetailsByGroupId(groupId)
            val groupMembers = repository.getGroupMembersByGroupId(groupId)

            groupMembers.forEach { member ->
                var balance = 0.0
                expensesInGroup.forEach { expense ->
                    if (expense.payerId == member.userId) {
                        balance += expense.amount
                    }
                }
                paymentsInGroup.forEach { payment ->
                    if (payment.payerId == member.userId) {
                        balance += payment.amount
                    }
                    if (payment.payeeId == member.userId) {
                        balance -= payment.amount
                    }
                }
                expenseDetails.forEach { expenseDetail ->
                    if (expenseDetail.payeeId == member.userId) {
                        balance -= expenseDetail.amount
                    }

                }
                repository.updateGroupMember(GroupMemberEntity(member.id, member.userId, member.groupId, balance))
            }
            _uiState.update {
                state -> state.copy(
                    groupMembers = repository.getGroupMembersByGroupId(groupId),
                    expensesInGroup = expensesInGroup,
                    paymentsInGroup = paymentsInGroup,
                    expenseDetails = expenseDetails
                )
            }
        }
    }

    fun addUserToGroup(groupId: String, userId: String) {
        viewModelScope.launch {
            val newGroupMemberEntity = GroupMemberEntity(id = UUID.randomUUID().toString(),
                groupId = groupId,
                userId = userId,
                balance = 0.0)
            repository.addGroupMember(newGroupMemberEntity)
            _uiState.update { state -> state.copy(groupMembers = state.groupMembers + newGroupMemberEntity) }
        }
    }

    fun groupInView(groupEntity: GroupEntity) {
        viewModelScope.launch {
            val expenses = repository.getExpensesByGroupId(groupEntity.id)
            val payments = repository.getPaymentsByGroupId(groupEntity.id)
            val groupMembers = repository.getGroupMembersByGroupId(groupEntity.id)
            val expenseDetails = repository.getExpenseDetailsByGroupId(groupEntity.id)
            _uiState.update { state -> state.copy(groupInView = groupEntity,
                expensesInGroup = expenses,
                paymentsInGroup = payments,
                groupMembers = groupMembers,
                expenseDetails = expenseDetails )}
            calculateBalances()
        }
    }

    fun expenseInView(expenseEntity: ExpenseEntity) {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(expenseInView = expenseEntity) }
        }
    }

    fun paymentInView(paymentEntity: PaymentEntity) {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(paymentInView = paymentEntity) }
        }
    }

    fun resetExpenseInView() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(expenseInView = null) }
        }
    }

    fun resetPaymentInView() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(paymentInView = null) }
        }
    }

    fun removeUserFromGroup(groupMemberId: String) {
        viewModelScope.launch {
            val groupMember = repository.getGroupMemberById(groupMemberId)
            repository.deleteGroupMember(groupMember!!)
            _uiState.update { state -> state.copy(groupMembers = repository.getGroupMembersByGroupId(state.groupInView?.id.toString())) }
        }
    }

    fun apiUsage() {
        viewModelScope.launch {
            try {
                val exchangeRates = Api.retrofitService.getExchangeRates()
                Log.i("EXCHANGE_RATES", "apiUsage: ${exchangeRates.rates}")
            } catch (e: Exception) {
                Log.e("EXCHANGE_RATES", "Error fetching exchange rates", e)
            }
        }
    }

    fun updateExpense(expenseId: String, amountValue: Double, title: String, selectedPayer: String) {
        viewModelScope.launch {
            val expense = repository.getExpenseById(expenseId)
            val updatedExpense = ExpenseEntity(
                id = expenseId,
                amount = amountValue,
                title = title,
                payerId = selectedPayer,
                groupId = _uiState.value.groupInView?.id.toString()
            )
            repository.updateExpense(updatedExpense)

            if (expense?.amount != amountValue) {
                val expenseDetails = repository.getExpenseDetailsByExpenseId(expenseId)
                repository.updateExpenseDetails(expenseDetails, amountValue)
            }
            expenseInView(updatedExpense)
            calculateBalances()
        }
    }
    
    fun updatePayment(paymentId: String, amountValue: Double, selectedPayer: String, selectedPayee: String) {
        viewModelScope.launch {
            val updatedPayment = PaymentEntity(
                id = paymentId,
                amount = amountValue,
                payerId = selectedPayer,
                payeeId = selectedPayee,
                groupId = _uiState.value.groupInView?.id.toString()
            )
            repository.updatePayment(updatedPayment)

            paymentInView(updatedPayment)
            calculateBalances()
        }
    }
}