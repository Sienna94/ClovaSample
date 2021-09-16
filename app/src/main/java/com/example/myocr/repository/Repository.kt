package com.example.myocr.repository

import com.example.myocr.vo.Field

interface Repository {
    fun getResult(data : String)
}