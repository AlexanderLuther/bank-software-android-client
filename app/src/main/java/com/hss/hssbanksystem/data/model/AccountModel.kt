package com.hss.hssbanksystem.data.model

data class AccountModel(
    val balance: String,
    val id_account: String,
    val id_account_type: Int,
    val movements: ArrayList<MovementModel>
)