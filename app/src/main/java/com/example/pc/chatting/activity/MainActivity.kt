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
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import android.util.Base64.NO_WRAP
import android.provider.MediaStore.Images
import android.provider.MediaStore.Images.Media.getBitmap
import java.io.BufferedInputStream
import java.net.URL
import android.R.attr.bitmap
import android.app.Activity
import android.app.PendingIntent.getActivity
import com.bumptech.glide.Glide
import android.graphics.BitmapFactory
import android.R.attr.bitmap
import com.example.pc.chatting.data.SignUp
import ninja.sakib.pultusorm.core.PultusORM
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException


class MainActivity : AppCompatActivity() {
    var token: String = ""
    var img: String = ""
    var email: String = ""
    var name: String = ""
    var phone: String = ""
    lateinit var pultusORM: PultusORM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pres: SharedPreferences = getSharedPreferences("pres", Context.MODE_PRIVATE)
        val editor = pres.edit()
        val appPath: String = applicationContext.filesDir.absolutePath
        pultusORM = PultusORM("user.db", appPath)
        try {
            token = intent.getStringExtra("token")
            Log.e("token", token)
        } catch (e: IllegalStateException) {
            token = pres.getString("token", "")
            Log.e("token", token)
        }

        token()

        val userList: List<Any> = pultusORM.find(SignUp())
        Log.e("suerList", userList.size.toString())
        var user = userList[userList.size - 1] as SignUp

        email = user.email
        name = user.name
        phone = user.phone
        img = user.profileImg
        mainEmail.text = mainEmail.text.toString() + email
        mainName.text = mainName.text.toString() + name
        mainPhone.text = mainPhone.text.toString() + phone
        Glide.with(this).load(img).into(mainImg)

        logout.setOnClickListener {
            editor.remove("token")
            editor.commit()
            pultusORM.drop(SignUp())
            System.exit(0)
        }
    }

    //서버연동
    fun token() {
        val res: Call<Token> = RetrofitUtil.postService.Token(token)
        res.enqueue(object : Callback<Token> {

            override fun onResponse(call: Call<Token>?, response: Response<Token>?) {
                Log.e("아 씨발", "로그야 찍혀라")
                if (response!!.code() == 200) {
                    response.body()?.let {
                        Log.e("로그찍음", "200")
                        pultusORM.save(response!!.body()!!.user)
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
