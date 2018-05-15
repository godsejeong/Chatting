package com.example.pc.chatting

import android.app.PendingIntent.getService
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.style.UnderlineSpan
import android.text.SpannableString
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import android.os.AsyncTask.execute
import okhttp3.OkHttpClient
import org.apache.http.params.HttpConnectionParams

class MainActivity : AppCompatActivity() {
    var retrofit : Retrofit? = null
    val SERVER_URL =  "http://iwin247.info:3000"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        val content = SpannableString("Sing Up")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        singUp.text = content
        //글꼴 변경
    }

    fun init(){

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor()

        retrofit = Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

                Log.e("init","in")
       var service = retrofit!!.create(RetrofitServer::class.java)

        //Log.e("repos",repos.toString())
    }
}
