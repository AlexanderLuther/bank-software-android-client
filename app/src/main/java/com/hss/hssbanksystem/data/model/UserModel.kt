package com.hss.hssbanksystem.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("address") val address: String,
    @SerializedName("birth_day") val birthDay: String,
    @SerializedName("civil_status") val civilStatus: String,
    @SerializedName("cui") val cui: Int,
    @SerializedName("gender") val gender: String,
    @SerializedName("name") val name: String,
    @SerializedName("ocupation") val ocupation: String,
    @SerializedName("phone_number") val phoneNumber: Int,
    @SerializedName("surname") val surname: String
)
