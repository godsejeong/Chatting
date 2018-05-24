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
            id = userId.text.toString()
            passwd = userPassword.text.toString()
            name = userName.text.toString()
            email = userEmail.text.toString()
            phone = userPhone.text.toString()

            if(id.isEmpty() || passwd.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()){
                Toast.makeText(applicationContext,"모든 항목을 기입하십시오.", Toast.LENGTH_SHORT).show()
            }else{
                signup()
            }
        }
    }

    fun signup(){
        val postService = RetrofitUtil.retrofit!!.create(RetrofitServer::class.java)
        val res : Call<SignUp> = postService.SignUp(
                userId.text,
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
