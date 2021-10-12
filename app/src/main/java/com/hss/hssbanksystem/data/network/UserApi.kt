package com.hss.hssbanksystem.data.network

import com.hss.hssbanksystem.data.model.UserModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.PUT

interface UserApi {

    @FormUrlEncoded
    @PUT("user")
    suspend fun uodatePassword(
        @Field("old_password") oldPassword: String,
        @Field("new_password") newPassword: String
    ) : UserModel

}