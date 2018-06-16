package com.example.pc.chatting.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pc.chatting.R
import com.example.pc.chatting.adapter.FrindListAdapter
import com.example.pc.chatting.data.*
import com.example.pc.chatting.util.RetrofitUtil
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_add_frind.*
import kotlinx.android.synthetic.main.frind_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFrindActivity : AppCompatActivity() {
    var frindEmail  = ""
    var token = ""
    var email = ""
    var phone = ""
    var img = ""
    var name = ""
    var context : Context = this
    var adapter: FrindListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_frind)
        var pres: SharedPreferences = getSharedPreferences("pres", Context.MODE_PRIVATE)
        token = pres.getString("token", "")




        addBtn.setOnClickListener {
            frindEmail = addFrindEt.text.toString()
            userFind()
        }
    }

    fun userFind(){
        val res : Call<FrindData> = RetrofitUtil.postService.UserFind(frindEmail)
        res.enqueue(object : Callback<FrindData>{

            override fun onResponse(call: Call<FrindData>?, response: Response<FrindData>?) {
                if(response!!.code() == 200 ){
                    email = response.body()!!.email
                    name = response.body()!!.name
                    img = response.body()!!.profileImg
                    phone = response.body()!!.phone

                    addItem()

                }else if(response!!.code() == 404) {
                    Toast.makeText(applicationContext, "친구가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext, "알 수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FrindData>?, t: Throwable?) {
                Toast.makeText(applicationContext, "서버 오류", Toast.LENGTH_SHORT).show()
                Log.e("retrofit Error", t!!.message)
            }
        })
    }

    fun addItem(){
        frindName.text = name
        Glide.with(this).load(img).into(frindImg)
        frindLayout.visibility = View.VISIBLE
        frindBtn.setOnClickListener{
            val res: Call<FrindAdd> = RetrofitUtil.postService.Useradd(email,token)
            res.enqueue(object : Callback<FrindAdd> {
                override fun onResponse(call: Call<FrindAdd>?, response: Response<FrindAdd>?) {
                    if(response!!.code() == 200) {
                        response.body()?.let {
                            Log.e("frindlog", token)
                            Log.e("Frindadd", Gson().toJson(response.body()!!))
                            Toast.makeText(context, "친구가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }else if(response!!.code() == 404){
                        Toast.makeText(context, "이미 추가 된 친구입니다.", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "알 수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<FrindAdd>?, t: Throwable?) {
                    Log.e("retrofit Error", t!!.message)
                    Toast.makeText(context,"서버 에러",Toast.LENGTH_SHORT).show()
                }
            })
        }

    }
}
