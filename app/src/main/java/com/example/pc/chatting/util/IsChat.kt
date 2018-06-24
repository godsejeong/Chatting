package com.example.pc.chatting.util

import android.util.Log
import com.example.pc.chatting.data.IsChatData
import retrofit2.Call


class IsChat(email: String, token: String) : Thread() {
    var email: String = ""
    var token: String = ""
    val res: Call<IsChatData> = RetrofitUtil.postService.IsChat(email,token)
    var ischat : Boolean? = null
    init {
        this.email = email
        this.token = token
    }

    override fun run() {
        when {
            res.clone().execute().code() == 200 -> ischat = res.execute().body()!!.isChat
            res.clone().execute().code() == 404 -> Log.e("mainischat","404")
            else -> Log.e("mainischat","error")
        }
    }

    fun ischat() : Boolean{
        return ischat!!
    }
}