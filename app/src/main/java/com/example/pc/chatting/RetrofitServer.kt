package com.example.pc.chatting

import android.text.Editable
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by pc on 2018-02-13.
 */
interface RetrofitServer {
    // iwin247.info:3000
    @FormUrlEncoded
    @POST("/signin")
    fun SignIn(@Field("id") id : String,@Field("password") pw : String) : Call<Signin>

}