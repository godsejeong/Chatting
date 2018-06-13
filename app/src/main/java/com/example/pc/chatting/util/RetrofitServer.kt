package com.example.pc.chatting.util

import android.text.Editable
import com.example.pc.chatting.data.SignIn
import com.example.pc.chatting.data.SignUp
import com.example.pc.chatting.data.Token
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded





/**
 * Created by pc on 2018-02-13.
 */
interface RetrofitServer {
    // iwin247.info:3000
    @FormUrlEncoded
    @POST("/signin")
    fun SignIn(@Field("email") email: Editable,
               @Field("passwd") passwd: Editable) : Call<SignIn>

    @Multipart
    @POST("/signup")
    fun SignUp(@Part("email") email : String,
               @Part("passwd") passwd : String,
               @Part("name") name: String,
               @Part("phone") phone: Int,
               @Part profileImg : MultipartBody.Part) : Call<SignUp>


    @GET("/auto/{token}")
    fun Token(@Path("token") token : String) : Call<Token>


    @FormUrlEncoded
    @POST("/add")
    fun Useradd(@Field("email") email : String) : Call<SignUp>


}