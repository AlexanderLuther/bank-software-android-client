package com.hss.hssbanksystem.data.model

import com.google.gson.annotations.SerializedName

data class CreditCardModel(
    @SerializedName("balance") val balance: String,
    @SerializedName("credit_limit") val creditLimit: String,
    @SerializedName("cutoff_date") val cutoffDate: String,
    @SerializedName("id_card") val idCard: String,
    @SerializedName("interest_rate") val interestRate: String,
    @SerializedName("payments") val payments: ArrayList<CardPaymentModel>,
    @SerializedName("payments_delayed") val paymentsDelayed: ArrayList<Any>
)