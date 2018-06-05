package com.example.pc.chatting.util

import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * Created by pc on 2018-05-18.
 */
object RetrofitUtil {

    val URL = "http://iwin247.info:3000"

    var retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val postService = retrofit!!.create(RetrofitServer::class.java)

    fun createMultipartBody(file: File, name: String): MultipartBody.Part {
        val mFile = RequestBody.create(MediaType.parse("images/*"), file)
        return MultipartBody.Part.createFormData(name, file.name, mFile)
    }


}