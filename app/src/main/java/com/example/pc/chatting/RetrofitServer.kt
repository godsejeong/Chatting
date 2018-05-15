package com.example.pc.chatting

import android.text.Editable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by pc on 2018-02-13.
 */
interface RetrofitServer {
    // iwin247.info:3000
    @POST("/signin")
    fun SignIn(): Call<Signin>
}