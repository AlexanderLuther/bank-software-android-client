package com.hss.hssbanksystem.data.network

import com.hss.hssbanksystem.data.model.UserModel
import retrofit2.http.*
import java.util.*

interface UserApi {

    @FormUrlEncoded
    @PUT("user")
    suspend fun updatePassword(
        @Field("old_password") oldPassword: String,
        @Field("new_password") newPassword: String
    ) : UserModel

    @FormUrlEncoded
    @POST("request/update_data")
    suspend fun updateData(
        @Field("address") address: String,
        @Field("phone_number") phoneNumber: String,
        @Field("civi_status") civilStatus: String,
        @Field("ocupation") occupation: String
    ) : UserModel

    @GET("person/information")
    suspend fun getUser(
    ) : UserModel

}