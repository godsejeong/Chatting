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
import kotlin.math.log

class RegisterActivity : AppCompatActivity() {
    var id : String = ""
    var passwd : String = ""
    var name : String = ""
    var email : String = ""
    var phone : String = ""
    var emptycheck : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resiger)
        userPassword.hint = "Password"
        userName.hint =  "User Name"
        userEmail.hint = "User Email"
        userPhone.hint = "Phone Number"

        backBtn.setOnClickListener{
            finish()
        }

        completeBtn.setOnClickListener {
            passwd = userPassword.text.toString()
            name = userName.text.toString()
            email = userEmail.text.toString()
            phone = userPhone.text.toString()

            if(passwd.length < 8 && passwd.length < 12){
                emptycheck = true
                userPassword.error = "8자에서 12자 이내로 입력하시오"
                userPassword.requestFocus()
            }

            if(!(email.contains("@"))){
                emptycheck = true
                userEmail.error = "이메일 형식에 맞게 입력하세요"
                userEmail.requestFocus()
            }

            if(email.isEmpty()) {
                emptycheck = true
                userEmail.error = "이메일을 입력하십시오"
                userEmail.requestFocus()
            }
            if(passwd.isEmpty()){
                emptycheck = true
                userPassword.error = "비밀번호를 입력하십시오"
                userPassword.requestFocus()
            }
            if(name.isEmpty()){
                emptycheck = true
                userName.error = "이름을 입력하십시오"
                userName.requestFocus()
            }
            if(phone.isEmpty()){
                emptycheck = true
                userPhone.error = "전화번호를 입력하십시오"
                userPhone.requestFocus()
            }
            if(!emptycheck){
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
