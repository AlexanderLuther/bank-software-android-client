package com.hss.hssbanksystem.data.model

import com.google.gson.annotations.SerializedName

data class DebitCardModel(
    @SerializedName("balance") val balance: String,
    @SerializedName("cui") val cui: String,
    @SerializedName("id_account") val idAccount: String,
    @SerializedName("id_card") val idCard: String,
    @SerializedName("payments") val payments: ArrayList<CardPaymentModel>
)