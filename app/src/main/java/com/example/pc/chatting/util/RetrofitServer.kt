package com.example.pc.chatting.util

import android.text.Editable
import com.example.pc.chatting.SignUp
import com.example.pc.chatting.Signin
import com.example.pc.chatting.data.Token
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by pc on 2018-02-13.
 */
interface RetrofitServer {
    // iwin247.info:3000
    @FormUrlEncoded
    @POST("/signin")
    fun SignIn(@Field("email") email: Editable,
               @Field("passwd") passwd: Editable) : Call<Signin>

    @FormUrlEncoded
    @POST("/signup")
    fun SignUp(@Field("email") email: Editable,
               @Field("passwd") passwd: Editable,
               @Field("name") name: Editable,
               @Field("phone") phone: Editable) : Call<SignUp>



    @GET("/auto/{token}")
    fun Token(@Path("token") token : String) : Call<Token>
}