package com.hss.hssbanksystem.data.model

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("token") val token: String,
    @SerializedName("user_type") val userType: Int,
    @SerializedName("username")val username: String
)