package com.hss.hssbanksystem.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class UserModel(
    @SerializedName("cui") val cui: Long,
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("address") val address: String,
    @SerializedName("phone_number") val phoneNumber: Long,
    @SerializedName("birth_day") val birthDay: Date,
    @SerializedName("gender") val gender: String,
    @SerializedName("civil_status") val civilStatus: String,
    @SerializedName("ocupation") val ocupation: String,
    @SerializedName("last_update_date") val lastUpdate: String


)
