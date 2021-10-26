package com.hss.hssbanksystem.data.repository

import com.hss.hssbanksystem.data.network.ServiceApi

class ServiceRepository (
    private val api: ServiceApi
) : BaseRepository() {

    suspend fun getServices() = safeApiCall {
        api.getServices()
    }

    suspend fun getAccount(id: String) = safeApiCall {
        api.getAccount(id)
    }

    suspend fun getLoan(id: String) = safeApiCall {
        api.getLoan(id)
    }

    suspend fun getCreditCard(id: String) = safeApiCall {
        api.getCreditCard(id)
    }

    suspend fun getDebitCard(id: String) = safeApiCall {
        api.getDebitCard(id)
    }

}