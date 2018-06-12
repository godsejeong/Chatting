package com.example.pc.chatting.activity

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pc.chatting.R
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.widget.Toast
import com.example.pc.chatting.data.Token
import com.example.pc.chatting.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import android.util.Base64.NO_WRAP
import android.provider.MediaStore.Images
import android.provider.MediaStore.Images.Media.getBitmap
import java.io.BufferedInputStream
import java.net.URL
import android.R.attr.bitmap
import android.app.Activity
import android.app.PendingIntent.getActivity
import com.bumptech.glide.Glide
import android.graphics.BitmapFactory
import android.R.attr.bitmap
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException


class MainActivity : AppCompatActivity() {
    var token = ""
    var img = ""
    var email = ""
    var name = ""
    var phone = ""
    var bitmap : Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pres : SharedPreferences = getSharedPreferences("pres", Context.MODE_PRIVATE)
        val editor = pres.edit()

        try {
            token = intent.getStringExtra("token")
            Log.e("token",token)
        }catch (e : IllegalStateException){
            token = pres.getString("token", "")
            Log.e("token",token)
        }

        val res: Call<Token> = RetrofitUtil.postService.Token(token)
        res.enqueue(object : Callback<Token> {

            override fun onResponse(call: Call<Token>?, response: Response<Token>?) {

                if (response!!.code() == 200) {
                    response.body()?.let {
                        email = response.body()!!.user.email
                        name = response.body()!!.user.name
                        img = response.body()!!.user.profileImg
                        phone = response.body()!!.user.phone


                        Imgload()
                        Log.e("mainEmail",email)
                        Log.e("mainName",name)
                        Log.e("mainImg",img.toString())
                        Log.e("mainPhone",phone.toString())
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<Token>?, t: Throwable?) {
                Log.e("retrofit Error!", t!!.message)
                Toast.makeText(applicationContext, "Sever Error", Toast.LENGTH_SHORT).show()
            }
        })
        Glide.with(this).load(img).into(mainImg)
        //token()
        Log.e("imagelog",img)
        //mainImg.setImageURI(img)
        //mainImg.setImageBitmap(bitmap)
        logout.setOnClickListener{

            editor.remove("token")
            editor.commit()
            System.exit(0)
        }
    }
    //서버연동
    fun token(){

    }

    fun Imgload(){
        val mThread = object : Thread() {
            override fun run() {
                try {
                    val url = URL(img)

                    // Web에서 이미지를 가져온 뒤
                    // ImageView에 지정할 Bitmap을 만든다
                    val conn = url.openConnection() as HttpURLConnection
                    conn.doInput = true // 서버로 부터 응답 수신
                    conn.connect()

                    val is_ = conn.inputStream // InputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is_) // Bitmap으로 변환

                } catch (e: MalformedURLException) {
                    e.printStackTrace()

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        mThread.start()

    }

}
