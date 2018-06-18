package com.example.pc.chatting.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pc.chatting.R
import kotlinx.android.synthetic.main.activity_sign_up_one.*

class SignUpOneActivity : AppCompatActivity() {
    var Email : String = ""
    var Passwd : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_one)

        signUpOneNext.setOnClickListener {
            Email = signUpEmail.text.toString()
            Passwd = signUpPasswd.text.toString()
            var intent = Intent(this, SignUpTwoActivity::class.java)
            intent.putExtra("email",Email)
            intent.putExtra("passwd",Passwd)
            startActivity(intent)
        }

        signUpOneBack.setOnClickListener {
            finish()
        }

    }
}
