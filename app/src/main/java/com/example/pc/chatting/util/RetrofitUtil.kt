package com.example.pc.chatting.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by pc on 2018-05-18.
 */
object RetrofitUtil {

    val URL = "http://iwin247.info:3000"

    var retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val MULTIPART_FORM_DATA = "multipart/form-data"
}