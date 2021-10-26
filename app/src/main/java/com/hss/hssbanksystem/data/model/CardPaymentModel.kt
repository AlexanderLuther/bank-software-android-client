package com.hss.hssbanksystem.data.model

import com.google.gson.annotations.SerializedName

data class CardPaymentModel(
    @SerializedName("amount") val amount: String,
    @SerializedName("date_time") val dateTime: String,
    @SerializedName("description") val description: String,
    @SerializedName("id_card") val idCard: String,
    @SerializedName("id_card_payment") val idCardPayment: Int
)