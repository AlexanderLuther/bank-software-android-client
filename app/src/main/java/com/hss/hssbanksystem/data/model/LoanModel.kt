package com.hss.hssbanksystem.data.model

import com.google.gson.annotations.SerializedName

data class LoanModel(
    @SerializedName("amount") val amount: String,
    @SerializedName("balance") val balance: String,
    @SerializedName("cui") val cui: String,
    @SerializedName("cutoff_date") val cutOffDate: String,
    @SerializedName("guarantor_cui") val guarantorCui: String,
    @SerializedName("id_loan") val idLoan: Int,
    @SerializedName("interest_rate") val interestRate: String,
    @SerializedName("monthly_payment") val monthlyPayment: String,
    @SerializedName("payments") val payments: ArrayList<PaymentLoanModel>
)