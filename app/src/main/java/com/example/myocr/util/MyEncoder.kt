package com.example.myocr.util

import android.graphics.Bitmap
import android.util.Base64
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream

class MyEncoder {
    //encoding image
    fun getBitmap(bm: Bitmap): Bitmap {
        var bitmap: Bitmap = bm
        return bitmap
    }

    fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 70, baos)
        val bImage = baos.toByteArray()
        val base64 = Base64.encodeToString(bImage, Base64.NO_WRAP)
        return base64
    }
}