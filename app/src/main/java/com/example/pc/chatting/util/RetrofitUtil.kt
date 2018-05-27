package com.example.pc.chatting.util

import com.example.pc.chatting.Signin
import com.example.pc.chatting.Token
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
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

//    fun autologin(){
//        val postService = RetrofitUtil.retrofit!!.create(RetrofitServer::class.java)
//        val res : Call<List<Token>> = postService.Token()
//        res.enqueue(object  : retrofit2.Callback<Token> {
//
//        }
//
//    }
}