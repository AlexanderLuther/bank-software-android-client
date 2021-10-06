package com.hss.hssbanksystem.data.model

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("error") val error: String,
)