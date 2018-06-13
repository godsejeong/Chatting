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
import com.example.pc.chatting.util.RetrofitUtil
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    var id: String = ""
    var passwd: String = ""
    var token : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

            var intent = Intent(this, MainActivity::class.java)
            val res: Call<SignIn> = RetrofitUtil.postService.SignIn(loginId.text, loginPassword.text)
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

                            intent.putExtra("token",token)
                            Log.e("토큰",token)
                            Toast.makeText(applicationContext, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                            startActivity(intent)

                            editer.commit()
                            //토큰값 저장
                        }
                    } else {
                        loginId.error = "다시 입력해 주세요"
                        loginPassword.error = "다시 입력해 주세요"
                        Toast.makeText(applicationContext, "아이디나 비밀번호를 다시 입력해 주세요", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SignIn>?, t: Throwable?) {
                    Log.e("retrofit Error", t!!.message)
                    Toast.makeText(applicationContext, "Sever Error", Toast.LENGTH_SHORT).show()
                }
            })
        }
}


