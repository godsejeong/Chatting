package com.example.pc.chatting

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up_one.*
import kotlinx.android.synthetic.main.activity_sign_up_two.*

class SignUpTwoActivity : AppCompatActivity() {
    var Name : String = ""
    var Phone : String = ""
    var Email : String = ""
    var Passwd : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_two)
            Email = intent.getStringExtra("email")
            Passwd = intent.getStringExtra("passwd")
        signUpOneNext.setOnClickListener {
            Name = signUpEmail.text.toString()
            Phone = signUpPhone.text.toString()
            var intent = Intent(this,SignUpTwoActivity::class.java)
            intent.putExtra("name",Name)
            intent.putExtra("phone",Phone)
            intent.putExtra("email",Email)
            intent.putExtra("passwd",Passwd)
            startActivity(intent)
        }

        signUpOneBack.setOnClickListener {
            finish()
        }

    }
}
