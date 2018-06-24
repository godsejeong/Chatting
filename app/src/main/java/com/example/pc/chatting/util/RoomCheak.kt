package com.example.pc.chatting.util

import android.util.Log
import com.example.pc.chatting.data.RoomId
import retrofit2.Call

class RoomCheak(email : String,token : String) : Thread(){
    var email: String = ""
    var token: String = ""
    val res: Call<RoomId> = RetrofitUtil.postService.RoomCheak(email,token)
    var cheak : String = ""

    init {
        this.email = email
        this.token = token
    }
    override fun run() {
        when {
            res.clone().execute().code() == 200 -> cheak = res.execute().body()!!.roomID
            res.clone().execute().code() == 404 -> Log.e("mainisroom","404")
            else -> Log.e("mainisroom","error")
        }
    }

    fun cheak() : String{
        return cheak
    }

}