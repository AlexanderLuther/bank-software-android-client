package com.hss.hssbanksystem.data.repository

import com.hss.hssbanksystem.data.network.ServiceApi

class ServiceRepository (
    private val api: ServiceApi
) : BaseRepository() {

    suspend fun getServices() = safeApiCall {
        api.getServices()
    }

}