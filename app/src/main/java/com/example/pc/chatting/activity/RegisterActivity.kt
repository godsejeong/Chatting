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
import java.util.regex.Pattern
import kotlin.math.log
import android.provider.MediaStore
import android.content.Intent



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

        userProfile.setOnClickListener{
            startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE),1)
        }

        backBtn.setOnClickListener{
            finish()
        }

        completeBtn.setOnClickListener {
            passwd = userPassword.text.toString()
            name = userName.text.toString()
            email = userEmail.text.toString()
            phone = userPhone.text.toString()

            var emails = ("^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$")//email 정규식
            var passwords = ("^(?=.*?[A-Za-z])(?=.*?[0-9]).{8,}$")//passwd 정규식
            var phones = ("^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$") //phone number 정규식
            val emailm = Pattern.matches(emails,userEmail.text)
            val phonem = Pattern.matches(phones,userPhone.text)
            val passwordm = Pattern.matches(passwords,userPassword.text)
            //정규식 변환

            Log.e("i", emailm.toString())

            if(!passwordm){
                emptycheck = true
                userPassword.error = "8자 이상 입력하고 숫자와 영문자를 적어도 하나 이상 입력해주세요"
                userPassword.requestFocus()
            }

            if(!emailm){
                emptycheck = true
                userEmail.error = "이메일 형식에 맞게 입력하세요"
                userEmail.requestFocus()
            }

            if(!phonem){
                emptycheck = true
                userPhone.error = "휴대폰번호 형식에 맞게 입력하세요" +
                        "ex)01000000000"
                userPhone.requestFocus()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
