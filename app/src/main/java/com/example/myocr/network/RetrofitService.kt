package com.example.myocr.network

import com.example.myocr.vo.*
import com.example.myocr.vo2.RequestData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {
    @Headers(
        "Content-Type: application/json",
        "X-OCR-SECRET: VlhMcGFxV09BU2x1UUdNanNLaWlWcVBUY3ZVcE1hS0U="
    )
    @POST("general")
    fun postRequest(
        @Body params: RequestData
    ): Call<Example>

}