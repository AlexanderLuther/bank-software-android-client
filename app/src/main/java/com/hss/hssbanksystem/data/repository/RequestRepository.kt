package com.hss.hssbanksystem.data.repository

import com.hss.hssbanksystem.data.network.RequestApi

class RequestRepository(
    private val api: RequestApi
) : BaseRepository() {

    suspend fun requestBankAccount(type: Int) = safeApiCall {
        api.requestBankAccount(type)
    }

    suspend fun requestLoan(amount: Double, income: Double, cause: String, cui: String) = safeApiCall {
        api.requestLoan(amount, income, cause, cui)
    }

    suspend fun requestCreditCard(income: Double, amount: Double) = safeApiCall {
        api.requestCreditCard(income, amount)
    }

    suspend fun requestDebitCard(id: String) = safeApiCall {
        api.requestDebitCard(id)
    }

    suspend fun requestCardCancellation(idCard: String, cardType: Int, cause: String) = safeApiCall {
        api.requestCardCancellation(idCard, cardType, cause)
    }

    suspend fun requestUpdateData(address: String, phoneNumber: Long, civilStatus: String, occupation: String) = safeApiCall {
        api.requestUpdateData(address, phoneNumber, civilStatus, occupation)
    }
}