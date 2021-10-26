package com.hss.hssbanksystem.data.network

import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import com.hss.hssbanksystem.data.model.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET("services/active")
    suspend fun getServices() : ServiceModel

    @GET("account/statement")
    suspend fun getAccount(
        @Query("id_account") id: String,
    ) : AccountModel

    @GET("loan/statement")
    suspend fun getLoan(
        @Query("id_loan") id: String,
    ) : LoanModel

    @GET("card/statement")
    suspend fun getCreditCard(
        @Query("id_card") id: String,
    ) : CreditCardModel

    @GET("card/statement")
    suspend fun getDebitCard(
        @Query("id_card") id: String,
    ) : DebitCardModel

}