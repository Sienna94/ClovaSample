package com.example.myocr.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitClient {
    private var instance: Retrofit? = null
    private var gson = GsonBuilder().setLenient().create()

    //server
    const val BASE_URL =
        "https://084e2ecf9cb5404ba853109654fe9dfe.apigw.ntruss.com/custom/v1/9617/234c077fe553be4126b18534bfd7ebc893088d958f88b3f25034259b3a3ab868/"
    const val KEY = "VlhMcGFxV09BU2x1UUdNanNLaWlWcVBUY3ZVcE1hS0U="

    //singleton
    fun getInstance(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return instance!!
    }
}