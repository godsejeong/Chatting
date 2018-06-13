package com.example.pc.chatting.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pc.chatting.R
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pc.chatting.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.pc.chatting.data.SignUp
import com.example.pc.chatting.data.Token
import com.example.pc.chatting.data.User
import com.google.gson.Gson
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
        Log.e("starttoken",token)
        val appPath: String = applicationContext.filesDir.absolutePath
        pultusORM = PultusORM("user.db", appPath)
        var pres: SharedPreferences = getSharedPreferences("pres", Context.MODE_PRIVATE)
        val editor = pres.edit()

        try {
            token = intent.getStringExtra("token")
            Log.e("token", token)
            token()
        } catch (e: IllegalStateException) {
            token = pres.getString("token", "")
            Log.e("token", token)
        }



        val userList: List<Any> = pultusORM.find(User())
          Log.e("suerList", userList.size.toString())
        if(userList.isNotEmpty()){
            var user= userList[userList.size - 1] as User
            email = user.email
            name = user.name
            phone = user.phone
            img = user.profileImg
            Log.e("mainEmail",email)
            Log.e("mainName",name)
            Log.e("mainPhone",phone)
            Log.e("mianImg",img)
            mainEmail.text = mainEmail.text.toString() + email
            mainName.text = mainName.text.toString() + name
            mainPhone.text = mainPhone.text.toString() + phone
            Glide.with(this).load(img).into(mainImg)
            //pultusORM.drop(Token())
            }

        logout.setOnClickListener {
            editor.remove("token")
            editor.commit()
            token = ""
            Log.e("endtoken",token)
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        frindAddFab.setOnClickListener {
            var intent = Intent(this,AddFrindActivity::class.java)
            startActivityForResult(intent,100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode === Activity.RESULT_OK){

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
                        pultusORM.delete(User())
                        pultusORM.save(response!!.body()!!.user!!)
                        Log.e("로그찍음", Gson().toJson(response!!.body()!!.user!!))
                        Log.e("로그찍음",pultusORM.save(response!!.body()!!.user!!).toString())
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
