package com.hss.hssbanksystem.data.repository

import com.hss.hssbanksystem.data.network.RequestApi

class RequestRepository(
    private val api: RequestApi
) : BaseRepository() {

    suspend fun requestBankAccount(type: Int) = safeApiCall {
        api.requestBankAccount(type)
    }

}