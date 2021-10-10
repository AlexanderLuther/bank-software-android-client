package com.hss.hssbanksystem.core

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    /**
     * URL base para la comunicacion con el servidor remoto
     */
    companion object {
        private const val BASE_URL = "https://analisis-bank-server.herokuapp.com/"
    }

    /**
     * Funcion que construye la api para la comunicacion con el servidor
     */
    fun <T> buildApi(
        api: Class<T>,
        authenticationToken: String? = null
    ): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor{ chain ->
                        chain.proceed(chain.request().newBuilder().also {
                            it.header("token", "$authenticationToken")
                        }.build())
                    }.also { client ->
                    if(BuildConfig.DEBUG){
                        val loggin = HttpLoggingInterceptor()
                        loggin.level = HttpLoggingInterceptor.Level.BODY
                        client.addInterceptor(loggin)
                    }
                }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}