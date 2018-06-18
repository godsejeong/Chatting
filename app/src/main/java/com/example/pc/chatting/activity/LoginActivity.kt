package com.example.pc.chatting.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.Toast
import com.example.pc.chatting.R
import com.example.pc.chatting.data.SignIn
import com.example.pc.chatting.data.Token
import com.example.pc.chatting.util.RetrofitUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import ninja.sakib.pultusorm.core.PultusORM
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var pultusORM : PultusORM
    var id: String = ""
    var passwd: String = ""
    var token : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val appPath: String = applicationContext.filesDir.absolutePath
        pultusORM = PultusORM("user.db", appPath)

        val content = SpannableString("Sing Up")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        signUpBtn.text = content
        //글꼴 변경

        signUpBtn.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginId.hint = "Email"
        loginPassword.hint = "Password"

        loginBtn.setOnClickListener {
                id = loginId.text.toString()
                passwd = loginPassword.text.toString()
                if (id.isEmpty()) {
                    loginId.error = "아이디를 입력해주세요"
                    loginId.requestFocus()
                } else if (passwd.isEmpty()) {
                    loginPassword.error = "비밀번호를 입력해주세요"
                    loginPassword.requestFocus()
                } else {
                    Log.e("post", "post")
                    signIn()
                }
        }
    }
        fun signIn() {
            val res: Call<SignIn> = RetrofitUtil.postService.SignIn(loginId.text,loginPassword.text)
            Log.e("asdf", res.request().toString())
            res.enqueue(object : retrofit2.Callback<SignIn> {
                override fun onResponse(call: Call<SignIn>?, response: Response<SignIn>?) {
                    Log.d("Retrofit", response!!.code().toString())
                    if (response!!.code() == 200) {
                        response.body()?.let {


                            var pres : SharedPreferences = getSharedPreferences("pres", Context.MODE_PRIVATE)
                            token = response.body()!!.token
                            var editer : SharedPreferences.Editor = pres.edit()
                            editer.putString("token",token)
                            editer.commit()
                            Log.e("토큰",token)
                            userToken()
                            Toast.makeText(applicationContext, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()

                            //토큰값 저장
                        }
                    } else {
                        loginId.error = "다시 입력해 주세요"
                        loginPassword.error = "다시 입력해 주세요"
                        Toast.makeText(applicationContext, "아이디나 비밀번호를 다시 입력해 주세요", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SignIn>?, t: Throwable?) {
                    Toast.makeText(applicationContext, "Sever Error", Toast.LENGTH_SHORT).show()
                    Log.e("retrofit Error", t!!.message)
                    if(t!!.message!!.contains("No address associated with hostname")){
                        Toast.makeText(applicationContext, "인터넷이 연결되지 않았습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

    fun userToken(){
            var intent = Intent(this, MainActivity::class.java)
            val res: Call<Token> = RetrofitUtil.postService.Token(token)
            res.enqueue(object : Callback<Token> {
                override fun onResponse(call: Call<Token>?, response: Response<Token>?) {
                    if (response!!.code() == 200) {
                        response.body()?.let {
                            pultusORM.save(response!!.body()!!.user!!)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(applicationContext, "알 수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Token>?, t: Throwable?) {
                    Log.e("retrofit Error!", t!!.message)
                    Toast.makeText(applicationContext, "Sever Error", Toast.LENGTH_SHORT).show()
                }
            })
    }
}


