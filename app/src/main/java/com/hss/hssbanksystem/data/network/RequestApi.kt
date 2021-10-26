package com.hss.hssbanksystem.data.network

import com.hss.hssbanksystem.data.model.RequestModel
import retrofit2.http.*

interface RequestApi {

    @FormUrlEncoded
    @POST("request/account")
    suspend fun requestBankAccount(
        @Field("account_type") type: Int
    ) : RequestModel

    @FormUrlEncoded
    @POST("request/loan")
    suspend fun requestLoan(
        @Field("amount") amount: Double,
        @Field("monthly_income") income: Double,
        @Field("cause") cause: String,
        @Field("guarantor_cui") cui: String
    ): RequestModel

    @FormUrlEncoded
    @POST("request/credit_card")
    suspend fun requestCreditCard(
        @Field("monthly_income") income: Double,
        @Field("desire_amount") amount: Double
    ) : RequestModel

    @FormUrlEncoded
    @POST("request/debit_card")
    suspend fun requestDebitCard(
        @Field("id_account") id: String
    ) : RequestModel

    @FormUrlEncoded
    @POST("request/card_cancellation")
    suspend fun requestCardCancellation(
        @Field("id_card") idCard: String,
        @Field("card_type") cardType: Int,
        @Field("cause") cause: String
    ) : RequestModel

    @FormUrlEncoded
    @POST("request/update_data")
    suspend fun requestUpdateData(
        @Field("address") address: String,
        @Field("phone_number") phoneNumber: Long,
        @Field("civil_status") civilStatus: String,
        @Field("ocupation") occupation: String
    ) : RequestModel

    @FormUrlEncoded
    @POST("account/transfer_on_app")
    suspend fun requestTransfer(
        @Field("id_origin_account") originAccount: Long,
        @Field("id_destination_account") destinationAccount: Long,
        @Field("amount") amount: Double
    ) : RequestModel
}