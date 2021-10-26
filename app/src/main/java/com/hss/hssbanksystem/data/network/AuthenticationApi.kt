package com.hss.hssbanksystem.data.network

import com.hss.hssbanksystem.data.model.AuthenticationModel
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthenticationApi {

    /**
     * Funcion que ejecuta una peticion https POST
     * @param username Cuerpo de la peticion
     * @param password Cuerpo de la peticion
     */
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ) : AuthenticationModel

    /**
     * Funcion que ejecuta una peticion https POST
     * @param username Cuerpo de la peticion
     * @param password Cuerpo de la peticion
     * @param userType Cuerpo de la peticion
     * @param cui Cuerpo de la peticion
     */
    @FormUrlEncoded
    @POST("user")
    suspend fun createUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("user_type") userType: Int,
        @Field("cui") cui: String,
        @Field("email") email: String
    ) : AuthenticationModel


    /**
     * Funcion que ejecuta una peticion http POST
     * @param username Nombre de usuario que desea recuperar su contraseña
     */
    @FormUrlEncoded
    @POST("user/password_recovery")
    suspend fun recoverPassword(
        @Field("username") username: String
    ) : AuthenticationModel

    /**
     * Funcion que ejecuta una peticion https POST
     */
    @POST("logout")
    suspend fun logout(): ResponseBody

}