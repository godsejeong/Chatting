package com.example.pc.chatting.activity

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pc.chatting.R
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.widget.Toast
import com.example.pc.chatting.data.Token
import com.example.pc.chatting.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson




class MainActivity : AppCompatActivity() {
    var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pres : SharedPreferences = getSharedPreferences("pres", Context.MODE_PRIVATE)
        val editor = pres.edit()

        try {
            token = intent.getStringExtra("token")
            Log.e("token",token)
        }catch (e : IllegalStateException){
            token = pres.getString("token", "")
            Log.e("token",token)
        }

        token()

        logout.setOnClickListener{

            editor.remove("token")
            editor.commit()
            System.exit(0)
        }
    }

    fun token(){
        val res: Call<Token> = RetrofitUtil.postService.Token(token)
        res.enqueue(object : Callback<Token> {

            override fun onResponse(call: Call<Token>?, response: Response<Token>?) {

                if (response!!.code() == 200) {
                    response.body()?.let {
                        var email = response.body()!!.user.email
                        var name = response.body()!!.user.name
                        var img = response.body()!!.user.img
                        var phone = response.body()!!.user.phone
                        Log.e("mainEmail",email)
                        Log.e("mainName",name)
                        Log.e("mainImg",img.toString())
                        Log.e("mainPhone",phone.toString())
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<Token>?, t: Throwable?) {
                Log.e("retrofit Error!", t!!.message)
                Toast.makeText(applicationContext, "Sever Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
