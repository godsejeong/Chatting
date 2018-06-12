package com.example.pc.chatting.activity

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pc.chatting.R
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pc.chatting.data.Token
import com.example.pc.chatting.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.pc.chatting.data.SignUp
import ninja.sakib.pultusorm.core.PultusORM


class MainActivity : AppCompatActivity() {
    lateinit var pultusORM : PultusORM
    var token: String = ""
    var img: String = ""
    var email: String = ""
    var name: String = ""
    var phone: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appPath: String = applicationContext.filesDir.absolutePath
        pultusORM = PultusORM("chatting.db", appPath)
        var pres: SharedPreferences = getSharedPreferences("pres", Context.MODE_PRIVATE)
        val editor = pres.edit()
        try {
            token = intent.getStringExtra("token")
            Log.e("token", token)
        } catch (e: IllegalStateException) {
            token = pres.getString("token", "")
            Log.e("token", token)
        }

        token()
        val userList: List<String> = pultusORM.find(SignUp()) as List<String>
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
            pultusORM.delete(SignUp())
            editor.remove("token")
            editor.commit()
            System.exit(0)
        }
    }

    //서버연동
   fun token() {
        Log.e("token__",token)
        val res: Call<Token> = RetrofitUtil.postService.Token(token)
        res.enqueue(object : Callback<Token> {
            override fun onResponse(call: Call<Token>?, response: Response<Token>?) {
                Log.e("아 씨발", "로그야 찍혀라")
                if (response!!.code() == 200) {
                    response.body()?.let {
                        Log.e("로그찍음", "200")
                        pultusORM.delete(SignUp::class.java)
                        pultusORM.save(response!!.body()!!.user)
                    }
                } else {
                    Log.e("로그찍음", "error")
                }
            }

            override fun onFailure(call: Call<Token>?, t: Throwable?) {
                Log.e("retrofit Error!", t!!.message)
                Toast.makeText(applicationContext, "Sever Error", Toast.LENGTH_SHORT).show()
            }
        })

    }

}
