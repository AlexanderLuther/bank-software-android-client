package com.hss.hssbanksystem.core

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object {
        private const val PORT = 3000
        private const val IP = "192.168.1.50"
        private const val BASE_URL = "http://$IP:$PORT/"
    }

    fun <T> buildApi(api: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder().also { client ->
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