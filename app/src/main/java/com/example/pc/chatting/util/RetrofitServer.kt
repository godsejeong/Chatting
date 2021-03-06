package com.example.pc.chatting.util

import android.text.Editable
import com.example.pc.chatting.data.*
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
               @Field("passwd") passwd: Editable): Call<SignIn>

    @Multipart
    @POST("/signup")
    fun SignUp(@Part("email") email: String,
               @Part("passwd") passwd: String,
               @Part("name") name: String,
               @Part("phone") phone: String,
               @Part profileImg : MultipartBody.Part) : Call<SignUp>


    @GET("/auto/{token}")
    fun Token(@Path("token") token: String): Call<Token>


    @FormUrlEncoded
    @POST("/search")
    fun UserFind(@Field("email") email: String): Call<FrindData>

    @FormUrlEncoded
    @POST("/add")
    fun Useradd(@Field("token") token: String,
                @Field("email") email: String): Call<FrindAdd>

    @FormUrlEncoded
    @POST("/ischat")
    fun Ischat(@Field("token") token : String,
               @Field("email") email : String) : Call<IsChatData>

    @FormUrlEncoded
    @POST("/chk")
    fun FrindList(@Field("token") token: String): Call<FrindAdd>

    @FormUrlEncoded
    @POST("/room")
    fun FrindRoom(@Field("email") email: String,
                  @Field("token") token: String): Call<RoomId>

    @FormUrlEncoded
    @POST("/ischat")
    fun IsChat(@Field("email") email: String,
               @Field("token") token: String): Call<IsChatData>

    @FormUrlEncoded
    @POST("/roomchk")
    fun RoomCheak(@Field("email") email: String,
                  @Field("token") token: String): Call<RoomId>


    @FormUrlEncoded
    @POST("/roomlist")
    fun FrindRoomList(@Field("email") email : String,
                  @Field("roomId") roomId : String) : Call<RoomId>


}