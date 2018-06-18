package com.example.pc.chatting.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pc.chatting.R
import kotlinx.android.synthetic.main.activity_sign_up_one.*
import java.util.regex.Pattern

class SignUpOneActivity : AppCompatActivity() {
    var Email : String = ""
    var Passwd : String = ""
    var emails = ("^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$")//email 정규식
    var passwords = ("^(?=.*?[A-Za-z])(?=.*?[0-9]).{8,}$")//passwd 정규식
    var emailbl = false
    var passwdbl = false
    var emptybl = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_one)

        signUpOneNext.setOnClickListener {

            Email = signUpEmail.text.toString()
            Passwd = signUpPasswd.text.toString()

            val emailm = Pattern.matches(emails,signUpEmail.text)
            val passwordm = Pattern.matches(passwords,signUpPasswd.text)

            if(!emailm){
                emailbl = true
                signUpEmail.error = "이메일 형식에 맞게 입력하세요"
                signUpEmail.requestFocus()
            }else{
                emailbl = false
            }

            if(Email.isEmpty()){
                emptybl = true
                signUpEmail.error = "이메일을 입력하십시오"
                signUpEmail.requestFocus()
            }else{
                emptybl = false
            }

            if(!passwordm){
                passwdbl = true
                signUpPasswd.error = "8자 이상 입력하고 숫자와 영문자를 적어도 하나 이상 입력해주세요"
                signUpPasswd.requestFocus()
            }else{
                passwdbl = false
            }

            if(Passwd.isEmpty()){
                emptybl = true
                signUpPasswd.error = "비밀번호를 입력하십시오"
                signUpPasswd.requestFocus()
            }else{
                emptybl = false
            }

            if(!emptybl && !passwdbl && !emailbl) {
                var intent = Intent(this,SignUpTwoActivity::class.java)
                intent.putExtra("email", Email)
                intent.putExtra("passwd", Passwd)
                startActivity(intent)
            }
        }

        signUpOneBack.setOnClickListener {
            finish()
        }

    }
}
