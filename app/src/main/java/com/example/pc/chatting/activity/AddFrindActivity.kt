package com.example.pc.chatting.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.pc.chatting.R
import com.example.pc.chatting.data.FrindAdd
import com.example.pc.chatting.data.SignUp
import com.example.pc.chatting.data.Token
import com.example.pc.chatting.util.RetrofitUtil
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_add_frind.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFrindActivity : AppCompatActivity() {
    var frindEmail  = ""
    var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_frind)
        var pres: SharedPreferences = getSharedPreferences("pres", Context.MODE_PRIVATE)
        token = pres.getString("token", "")

        addBtn.setOnClickListener {
            frindEmail = addFrindEt.text.toString()
            addFrind()
            Log.e("finrdEmail",frindEmail)
            //Log.e("mytoken",token)
        }
    }

    fun addFrind(){
        val res: Call<FrindAdd> = RetrofitUtil.postService.Useradd(token,frindEmail)
        res.enqueue(object : Callback<FrindAdd>{
            override fun onResponse(call: Call<FrindAdd>?, response: Response<FrindAdd>?) {
                if(response!!.code() == 200){
                    response.body()?.let {

                        Log.e("frindlog",token)
                        Log.e("Frindadd",Gson().toJson(response.body()!!))
                        Toast.makeText(applicationContext, "친구가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }else if(response!!.code() == 404 ){
                    Log.e("frindlog",token)
                    Toast.makeText(applicationContext, "친구가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                }else if(response!!.code() == 500 ){
                    Toast.makeText(applicationContext, "알 수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FrindAdd>?, t: Throwable?) {
                Log.e("retrofit Error", t!!.message)}
        })
    }
}
