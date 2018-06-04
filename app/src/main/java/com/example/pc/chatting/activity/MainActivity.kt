package com.example.pc.chatting.activity

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pc.chatting.R
import kotlinx.android.synthetic.main.activity_main.*
import android.R.id.edit




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logout.setOnClickListener{
            var pres : SharedPreferences = getSharedPreferences("pres", Context.MODE_PRIVATE)
            val editor = pres.edit()
            editor.remove("token")
            editor.commit()
            System.exit(0)
        }
    }
}
