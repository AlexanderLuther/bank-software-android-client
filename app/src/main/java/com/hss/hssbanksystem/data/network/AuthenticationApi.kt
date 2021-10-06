package com.hss.hssbanksystem.data.network

import com.hss.hssbanksystem.data.model.AuthenticationModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticationApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ) : AuthenticationModel

}