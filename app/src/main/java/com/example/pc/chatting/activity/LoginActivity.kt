package com.example.pc.chatting.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.pc.chatting.R
import com.example.pc.chatting.Signin
import com.example.pc.chatting.util.RetrofitServer
import com.example.pc.chatting.util.RetrofitUtil
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    var id : String = ""
    var passwd : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        loginBtn.setOnClickListener {
            Log.e("onclick","시발")
            id = loginName.text.toString()
            passwd = loginPassword.text.toString()
            if (id.isEmpty() || passwd.isEmpty()) {
                Toast.makeText(applicationContext, "아이디와 비밀번호를 모두 기입해 주세요", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("post", "post")
                signIn()
            }
        }

        signUpBtn.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val content = SpannableString("Sing Up")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        signUpBtn.text = content
        //글꼴 변경
    }

    fun signIn(){

        var intent = Intent(this, MainActivity::class.java)
        val postService = RetrofitUtil.retrofit!!.create(RetrofitServer::class.java)
        val res : Call<Signin> = postService.SignIn(loginName.text,loginPassword.text)
        Log.e("asdf", res.request().toString())
        res.enqueue(object : retrofit2.Callback<Signin> {
            override fun onResponse(call: Call<Signin>?, response: Response<Signin>?) {
                Log.d("Retrofit", response!!.code().toString())

                if(response!!.code() == 200){
                    response.body()?.let {
                                Toast.makeText(applicationContext,"로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                startActivity(intent) }
                }else{
                    Toast.makeText(applicationContext,"아이디나 비밀번호를 다시 입력해 주세요", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Signin>?, t: Throwable?) {
                Log.e("retrofit Error", t!!.message)
                Toast.makeText(applicationContext,"Sever Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
