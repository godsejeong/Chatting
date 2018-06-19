package com.example.pc.chatting.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.ColorSpace
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.LinearLayoutManager
import com.example.pc.chatting.R
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import ninja.sakib.pultusorm.core.PultusORM
import android.widget.ImageView
import android.widget.Toast
import com.example.pc.chatting.adapter.RecyclerAdapter
import com.example.pc.chatting.data.*
import com.example.pc.chatting.util.RetrofitUtil
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import org.json.simple.parser.JSONParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var pultusORM : PultusORM
    var token: String = ""
    var img: String = ""
    var email: String = ""
    var name: String = ""
    var phone: String = ""
    var frindname : String = ""
    var frindImg : String = ""

    var items : ArrayList<FrindItemData> = ArrayList()
    var responsedata : ArrayList<FrindGetData> = ArrayList()

    var adapterContext : Context = this
    var layoutmanager = LinearLayoutManager(this)

    var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("starttoken",token)
        setSupportActionBar(mainToolbar)
        mainRecyclerView.layoutManager = layoutmanager
//        var adapter = RecyclerAdapter(items,this)


        var pres: SharedPreferences = getSharedPreferences("pres", Context.MODE_PRIVATE)
        val editor = pres.edit()




        val appPath: String = applicationContext.filesDir.absolutePath
        pultusORM = PultusORM("user.db", appPath)
        token = pres.getString("token", "")

        var toggle = ActionBarDrawerToggle(this,mainDrawer,mainToolbar,R.string.opentext,R.string.closetext)
        mainDrawer.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = false
        toggle.toolbarNavigationClickListener = View.OnClickListener{
            mainDrawer.openDrawer(GravityCompat.START)
        }
        toggle.setHomeAsUpIndicator(R.drawable.drawericon)

        bringList()
        var menu = nav_view.menu
        var navPhone = menu.findItem(R.id.nav_phone)
        var navEmail = menu.findItem(R.id.nav_email)
        var navLogout = menu.findItem(R.id.nav_logout)

        val header = nav_view.getHeaderView(0)
        var hearderText = header.findViewById(R.id.navName) as TextView
        var hearderImage = header.findViewById(R.id.navImage) as ImageView

        navLogout.setOnMenuItemClickListener{
            editor.remove("token")
            editor.commit()
            finish()
            var intent = Intent(this,SelectActivity::class.java)
            startActivity(intent)
            token = ""
            pultusORM.drop(User())
        }

        val userList: List<Any> = pultusORM.find(User())
          Log.e("suerList", userList.size.toString())
        if(userList.isNotEmpty()){
            var user= userList[userList.size - 1] as User
            email = user.email
            name = user.name
            phone = user.phone
            img = user.profileImg

            navEmail.title = email
            navPhone.title =  phone
            hearderText.text = name

            Glide.with(this).load(img).into(hearderImage)
            }

        frindAddFab.setOnClickListener {
            var intent = Intent(this,AddFrindActivity::class.java)
            startActivityForResult(intent,100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode === Activity.RESULT_OK){
            bringList()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        Log.e("click","clickclick")
        when(item.itemId){

            R.id.nav_logout -> {
            }
        }
        mainDrawer.closeDrawer(GravityCompat.START)
        return true
    }

    //서버연동
    fun bringList(){
        var adapter = RecyclerAdapter(items,adapterContext)
        val res: Call<FrindAdd> = RetrofitUtil.postService.FrindList(token)
        res.enqueue(object : Callback<FrindAdd> {

            override fun onResponse(call: Call<FrindAdd>?, response: Response<FrindAdd>?) {
              if(response!!.code() == 200){

                  Log.e("asdfdata",Gson().toJson(response.body()!!))

                  var obj = JSONObject(Gson().toJson(response.body()!!))
                  var title = obj.getJSONArray("list") as JSONArray

                  for(i in 0 until  title.length()){
                      var arr = title.getJSONObject(i) as JSONObject
                      var name =  arr.getString("name") as String
                      var profile =  arr.getString("profileImg") as String
                    items.add(FrindItemData(name,profile,"친구가 되었습니다.",SimpleDateFormat("a hh:mm").format(Date()),0))
                  }
                  mainRecyclerView.adapter = adapter
              }else if(response.code() == 404){

              }
            }

            override fun onFailure(call: Call<FrindAdd>?, t: Throwable?) {
                Log.e("retrofit Error!", t!!.message)
                Toast.makeText(applicationContext, "Sever Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
