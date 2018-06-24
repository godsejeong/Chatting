package com.example.pc.chatting.util

import android.util.Log
import com.example.pc.chatting.data.RoomId
import retrofit2.Call

class CreateRoom(email : String,token : String) : Thread(){
    var email: String = ""
    var token: String = ""
    val res: Call<RoomId> = RetrofitUtil.postService.FrindRoom(email,token)
    var room : String = ""
    init {
        this.email = email
        this.token = token
    }

    override fun run() {
        when {
            res.clone().execute().code() == 200 -> {
                Log.e("room200",res.execute().body()!!.roomID)
                room = res.execute().body()!!.roomID}
            res.clone().execute().code() == 404 -> Log.e("mainisroom","404")
            res.clone().execute().code() == 409 -> Log.e("mainisroom","409")
            else -> Log.e("mainisroom","error")
        }
    }

    fun room() : String{
        return room
    }

}