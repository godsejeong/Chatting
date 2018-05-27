package com.example.pc.chatting.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pc.chatting.R
import com.example.pc.chatting.SignUp
import com.example.pc.chatting.util.RetrofitServer
import com.example.pc.chatting.util.RetrofitUtil
import kotlinx.android.synthetic.main.activity_resiger.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    var id : String = ""
    var passwd : String = ""
    var name : String = ""
    var email : String = ""
    var phone : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resiger)

        completeBtn.setOnClickListener {
            passwd = userPassword.text.toString()
            name = userName.text.toString()
            email = userEmail.text.toString()
            phone = userPhone.text.toString()

            if(passwd.length < 8 && passwd.length < 12){
                Toast.makeText(applicationContext, "비밀번호를 8자에서 12자 이내로 입력하시오", Toast.LENGTH_SHORT).show()
            }else if(!(email.contains("@"))){
                Toast.makeText(applicationContext, "이메일 형식에 맞게 입력하시오", Toast.LENGTH_SHORT).show()
            }

            if(email.isEmpty()) {
                Toast.makeText(applicationContext, "아이디를 입력하십시오.", Toast.LENGTH_SHORT).show()
                userEmail.requestFocus()
            }else if(passwd.isEmpty()){
                Toast.makeText(applicationContext, "비밀번호를 입력하십시오.", Toast.LENGTH_SHORT).show()
                userPassword.requestFocus()
            }else if(name.isEmpty()){
                Toast.makeText(applicationContext, "이름을 입력하십시오.", Toast.LENGTH_SHORT).show()
                userName.requestFocus()
            }else if(phone.isEmpty()){
                Toast.makeText(applicationContext, "휴대폰번호를 입력하십시오.", Toast.LENGTH_SHORT).show()
                userPhone.requestFocus()
            }else{
                signup()
            }
        }
    }

    fun signup(){
        val postService = RetrofitUtil.retrofit!!.create(RetrofitServer::class.java)
        val res : Call<SignUp> = postService.SignUp(
                userPassword.text,
                userName.text,
                userEmail.text,
                userPhone.text
        )
        res.enqueue(object : Callback<SignUp>{
            override fun onResponse(call: Call<SignUp>?, response: Response<SignUp>?) {
                Log.e("register",response!!.code().toString())
                if(response!!.code() == 200){
                    Toast.makeText(applicationContext,"회원가입이 정상적으로 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }else if(response!!.code() == 409){
                    Toast.makeText(applicationContext,"이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show()
                }else if(response!!.code() == 400){
                    Toast.makeText(applicationContext,"Server Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignUp>?, t: Throwable?) {
                Log.e("retrofit Error", t!!.message)
            }

        })
    }
}
