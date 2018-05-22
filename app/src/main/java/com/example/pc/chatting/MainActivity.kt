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
import com.example.pc.chatting.R.id.singUp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.http.params.HttpConnectionParams
import android.os.AsyncTask.execute
import okhttp3.Callback
import retrofit2.Response




class MainActivity : AppCompatActivity() {
    var retrofit : Retrofit? = null
    val URL = "http://iwin247.info:3000"
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
//        var logging = HttpLoggingInterceptor()
//        logging.level = HttpLoggingInterceptor.Level.BODY
//
//        var httpClient = OkHttpClient.Builder()
//        httpClient.addInterceptor(logging)

        retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val postService = retrofit!!.create(RetrofitServer::class.java)
        val res : Call<Signin> = postService.SignIn("akat32","akat32!")

        res.enqueue(object : retrofit2.Callback<Signin> {
            override fun onResponse(call: Call<Signin>?, response: Response<Signin>?) {
                var signin  = response!!.body()!!.id
                Log.e("siginin_id",signin)
            }

            override fun onFailure(call: Call<Signin>?, t: Throwable?) {
                Log.e("retrofit Error", t!!.message)
            }

        })

    }
}
