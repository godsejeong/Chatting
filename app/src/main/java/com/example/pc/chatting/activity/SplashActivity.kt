package com.example.pc.chatting.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.pc.chatting.R
import com.example.pc.chatting.data.Token
import com.example.pc.chatting.util.RetrofitServer
import com.example.pc.chatting.util.RetrofitUtil
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val handler = Handler()
        handler.postDelayed({

            var pres: SharedPreferences = getSharedPreferences("pres", Context.MODE_PRIVATE)
            var token: String = pres.getString("token", "")
            //Log.e("자동로그인", token)

            var successintent = Intent(this, MainActivity::class.java)
            var failintent = Intent(this,LoginActivity::class.java)
            val res: Call<Token> = RetrofitUtil.postService.Token(token)
            res.enqueue(object : Callback<Token> {

                override fun onResponse(call: Call<Token>?, response: Response<Token>?) {
                    if (response!!.code() == 200) {
                            response.body()?.let {
                            Toast.makeText(applicationContext, "자동로그인이 되었습니다.", Toast.LENGTH_SHORT).show()
                            startActivity(successintent)
                            Log.e("auto", "자동로그인 완료")
                        }
                    } else {
                        startActivity(failintent)
                        Log.e("auto", "자동로그인 비완료")
                    }
                }

                override fun onFailure(call: Call<Token>?, t: Throwable?) {
                    Log.e("retrofit Error!!", t!!.message)
                    startActivity(failintent)
                    Toast.makeText(applicationContext,"Sever Error", Toast.LENGTH_SHORT).show()
                }
            })
        }, 3000)

    }
}
