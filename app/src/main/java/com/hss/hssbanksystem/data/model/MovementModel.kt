package com.hss.hssbanksystem.data.model

import com.google.gson.annotations.SerializedName

data class MovementModel(
    @SerializedName("amount") val amount: String,
    @SerializedName("date_time") val date: String,
    @SerializedName("movement_type") val type: String
)