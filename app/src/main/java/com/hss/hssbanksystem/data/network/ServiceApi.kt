package com.hss.hssbanksystem.data.network

import com.hss.hssbanksystem.data.model.ServiceModel
import retrofit2.http.GET

interface ServiceApi {

    @GET("services/active")
    suspend fun getServices() : ServiceModel

}