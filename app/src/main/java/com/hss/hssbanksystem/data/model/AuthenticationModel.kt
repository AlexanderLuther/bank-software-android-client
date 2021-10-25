package com.hss.hssbanksystem.data.model

import com.google.gson.annotations.SerializedName

data class AuthenticationModel(
    @SerializedName("token") val token: String,
    @SerializedName("username") val username: String,
    @SerializedName("user_type") val userType: Int
)