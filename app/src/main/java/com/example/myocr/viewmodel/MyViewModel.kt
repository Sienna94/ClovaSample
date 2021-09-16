package com.example.myocr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myocr.repository.RepositoryImpl
import com.example.myocr.vo.Field

class MyViewModel : ViewModel() {
    //live data
    val liveData_String: MutableLiveData<java.lang.StringBuilder> =
        MutableLiveData<java.lang.StringBuilder>()

    //repository
    var repository = RepositoryImpl

    fun setInferred(data: String) {
        val result = StringBuilder()
        repository.getResult(data)
        repository.onReturn = {
            for (field in it) {
                val infertext = field.inferText
                val inferConfidence = field.inferConfidence
                val item = "$infertext /$inferConfidence"
                result.append(item)
                result.append(System.getProperty("line.separator"))
            }
            liveData_String.postValue(result)
        }
    }
}