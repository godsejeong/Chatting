package com.example.pc.chatting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.style.UnderlineSpan
import android.text.SpannableString
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import android.widget.Toast
import retrofit2.Response




class MainActivity : AppCompatActivity() {
    var retrofit : Retrofit? = null
    val URL = "http://iwin247.info:3000"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginBtn.setOnClickListener(
            View.OnClickListener {
                Log.e("post","post")
                post()
        })

        val content = SpannableString("Sing Up")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        singUp.text = content
        //글꼴 변경
    }

    fun post(){
        retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val postService = retrofit!!.create(RetrofitServer::class.java)
        val res : Call<Signin> = postService.SignIn(loginName.text,loginPassword.text)
        Log.e("asdf", res.request().toString())
        res.enqueue(object : retrofit2.Callback<Signin> {
            override fun onResponse(call: Call<Signin>?, response: Response<Signin>?) {
                Log.d("Retrofit", response.toString())
                if(response!!.isSuccessful){
                    if(response!!.body() != null){
                        Toast.makeText(applicationContext,"로그인이 완료되었습니다.",Toast.LENGTH_SHORT).show()

                    }
                }else{
                    Toast.makeText(applicationContext,"아이디나 비밀번호를 다시 입력해 주세요",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Signin>?, t: Throwable?) {
                Log.e("retrofit Error", t!!.message)
            }

        })
    }
    //        var logging = HttpLoggingInterceptor()
//        logging.level = HttpLoggingInterceptor.Level.BODY
//
//        var httpClient = OkHttpClient.Builder()
//        httpClient.addInterceptor(logging)
}
