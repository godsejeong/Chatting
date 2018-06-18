package com.example.pc.chatting.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pc.chatting.R
import kotlinx.android.synthetic.main.activity_sign_up_two.*
import java.util.regex.Pattern

class SignUpTwoActivity : AppCompatActivity() {
    var Name : String = ""
    var Phone : String = ""
    var Email : String = ""
    var Passwd : String = ""
    var phones = ("^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-[0-9]{3,4}-[0-9]{4}$") //phone number 정규식
    var phonebl = false
    var emptybl = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_two)

            Email = intent.getStringExtra("email")
            Passwd = intent.getStringExtra("passwd")

        signUpTwoNext.setOnClickListener {
            Name = signUpName.text.toString()
            Phone = signUpPhone.text.toString()

            val phonem = Pattern.matches(phones,signUpPhone.text)

            if(!phonem){
                phonebl = true
                signUpPhone.error = "전화번호 형식에 맞게 입력하세요" +
                        "ex)01000000000"
                signUpPhone.requestFocus()
            }else{
                phonebl = false
            }

            if(Phone.isEmpty()){
                emptybl = true
                signUpPhone.error = "전화번호를 입력하십시오"
                signUpPhone.requestFocus()
            }else{
                emptybl = false
            }

            if(Name.isEmpty()){
                emptybl = true
                signUpName.error = "이름을 입력하십시오"
                signUpName.requestFocus()
            }else{
                emptybl = false
            }

            if(!emptybl && !phonebl){
                var intent = Intent(this,SignUpThreeActivity::class.java)
                intent.putExtra("name",Name)
                intent.putExtra("phone",Phone)
                intent.putExtra("email",Email)
                intent.putExtra("passwd",Passwd)
                startActivity(intent)
            }
        }

        signUpTwoBack.setOnClickListener {
            finish()
        }

    }
}
