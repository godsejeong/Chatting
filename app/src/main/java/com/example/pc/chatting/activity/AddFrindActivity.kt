package com.example.pc.chatting.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.pc.chatting.R
import com.example.pc.chatting.adapter.FrindListAdapter
import com.example.pc.chatting.data.*
import com.example.pc.chatting.util.RetrofitUtil

import kotlinx.android.synthetic.main.activity_add_frind.*
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
    var frindItems: ArrayList<FrindListData> = ArrayList()
    var adapter: FrindListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_frind)
        var pres: SharedPreferences = getSharedPreferences("pres", Context.MODE_PRIVATE)
        token = pres.getString("token", "")

        addBtn.setOnClickListener {
            frindEmail = addFrindEt.text.toString()
            userFind()
            Log.e("frind",frindItems.toString())
        }
    }

    fun userFind(){
        val res : Call<FrindData> = RetrofitUtil.postService.UserFind(frindEmail)
        res.enqueue(object : Callback<FrindData>{

            override fun onResponse(call: Call<FrindData>?, response: Response<FrindData>?) {
                if(response!!.code() == 200 ){
                    email = response!!.body()!!.email
                    name = response!!.body()!!.name
                    img = response!!.body()!!.profileImg
                    phone = response!!.body()!!.phone
                    frindItems.add(FrindListData(name,img))
                    adapter = FrindListAdapter(context,token,frindItems,R.layout.frind_list_item)
                    frindList.adapter= adapter
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
}
