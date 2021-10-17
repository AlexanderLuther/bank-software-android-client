package com.hss.hssbanksystem.data.network

import com.hss.hssbanksystem.core.DataStoreHelper
import com.hss.hssbanksystem.data.model.RequestModel
import retrofit2.http.*

interface RequestApi {

    @FormUrlEncoded
    @POST("request/account")
    suspend fun requestBankAccount(
        @Field("account_type") type: Int
    ) : RequestModel
}