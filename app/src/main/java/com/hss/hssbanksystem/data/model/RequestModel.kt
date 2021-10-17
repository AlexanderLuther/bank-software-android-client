package com.hss.hssbanksystem.data.model

import com.google.gson.annotations.SerializedName

data class RequestModel (
    @SerializedName("information_message") val message: String
)
