package com.example.pc.chatting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.style.UnderlineSpan
import android.text.SpannableString
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val content = SpannableString("Sing Up")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        singUp.text = content
    }
}
