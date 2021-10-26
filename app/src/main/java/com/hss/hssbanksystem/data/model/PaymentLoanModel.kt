package com.hss.hssbanksystem.data.model

import com.google.gson.annotations.SerializedName

data class PaymentLoanModel(
    @SerializedName("amount") val amount: String,
    @SerializedName("balance") val balance: String,
    @SerializedName("date") val date: String,
    @SerializedName("id_loan") val idLoan: Int,
    @SerializedName("id_payment") val idPayment: Int,
    @SerializedName("total_payment") val totalPayment: String
)