package com.example.pc.chatting.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pc.chatting.R
import kotlinx.android.synthetic.main.activity_select.*

class SelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        selectLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        selectSignUp.setOnClickListener {
            startActivity(Intent(this,SignUpOneActivity::class.java))
        }

    }
}
