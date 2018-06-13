package com.example.pc.chatting.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.pc.chatting.R
import com.example.pc.chatting.data.SignUp
import com.example.pc.chatting.util.RetrofitUtil
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_add_frind.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFrindActivity : AppCompatActivity() {
    var frindEmail  = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_frind)

        frindEmail = addFrindEt.text.toString()

        val res: Call<SignUp> = RetrofitUtil.postService.Useradd(frindEmail)
        res.enqueue(object : Callback<SignUp>{
            override fun onResponse(call: Call<SignUp>?, response: Response<SignUp>?) {
                if(response!!.code() == 200){
                    response.body()?.let {
                        Log.e("Frindadd",Gson().toJson(response.body()))
                    }
                }else{

                }
            }

            override fun onFailure(call: Call<SignUp>?, t: Throwable?) {
                Log.e("retrofit Error", t!!.message)}

        })
    }
}
