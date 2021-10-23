package com.hss.hssbanksystem.data.network

import com.hss.hssbanksystem.data.model.UserModel
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Field


interface UserApi {

    @GET("person/information")
    suspend fun getUser() : UserModel

    @FormUrlEncoded
    @PUT("user")
    suspend fun updatePassword(
        @Field("old_password") oldPassword: String,
        @Field("new_password") newPassword: String
    ) : UserModel

}