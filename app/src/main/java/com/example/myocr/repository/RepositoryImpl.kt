package com.example.myocr.repository

import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import com.example.myocr.network.RetrofitClient
import com.example.myocr.network.RetrofitService
import com.example.myocr.vo.Example
import com.example.myocr.vo.Field
import com.example.myocr.vo2.RequestData
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

object RepositoryImpl : Repository {
    //retrofit
    private val retrofit = RetrofitClient.getInstance()
    private val retrofitService = retrofit.create(RetrofitService::class.java)
    var requestData = RequestData()
    var result = ArrayList<Field>()
    var onReturn : ((ArrayList<Field>) -> Unit) ?= null

    override fun getResult(data: String){
        result.clear()

        //set RequestData
        val imageData = mutableListOf<com.example.myocr.vo2.Image>()
        imageData.add(com.example.myocr.vo2.Image().apply {
            this.format = "png"
            this.data = data
            this.name = "test"
        })
        requestData = RequestData().apply {
            this.version = "V1"
            this.requestId = "test"
            this.timestamp = 0
            this.images = imageData
        }

        //post request
        retrofitService.postRequest(requestData).enqueue(object : retrofit2.Callback<Example> {
            override fun onResponse(call: Call<Example>, response: Response<Example>) {
                Log.d("ddd", "Response_Code : ${response.code()} == ${response.message()}")
                Log.d(
                    "ddd",
                    "Header : ${
                        retrofitService.postRequest(requestData).request().headers
                    }"
                )
                Log.d(
                    "ddd",
                    "Url : ${retrofitService.postRequest(requestData).request().url}"
                )
                if (response.isSuccessful) {
                    Log.d("ddd", "onResponse: response= ${response.body()}")

                    response.body()?.let {
                        for (field in it.images[0].fields) {
                            result.add(field)
                        }
                    }
                    onReturn?.invoke(result)
                    Log.d("doja", "result: $result")

                } else {
                    Log.d("ddd", "onResponse: notSuccessful")
                }
            }

            override fun onFailure(call: Call<Example>, t: Throwable) {
                Log.d("ddd", "onFailure: failed")
            }
        })
    }
}