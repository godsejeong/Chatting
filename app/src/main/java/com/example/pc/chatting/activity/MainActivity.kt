package com.example.pc.chatting.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.eclipsesource.json.Json
import com.example.pc.chatting.adapter.RecyclerAdapter
import com.example.pc.chatting.data.*
import com.example.pc.chatting.util.IsChat
import com.example.pc.chatting.util.RetrofitUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.content_drawer.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.transform.Result
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var pultusORM: PultusORM
    var token: String = ""
    var img: String = ""
    var email: String = ""
    var name: String = ""
    var phone: String = ""
    var frindname: String = ""
    var frindImg: String = ""

    var items: ArrayList<FrindItemData> = ArrayList()
    var responsedata: ArrayList<FrindGetData> = ArrayList()

    var adapterContext: Context = this
    var layoutmanager = LinearLayoutManager(this)

    var test: ArrayList<ChatSendData> = ArrayList()
    var isbool: Boolean? = null

    var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("starttoken", token)
        setSupportActionBar(mainToolbar)


//        mainRecyclerView.setOnClickListener {
//
//        }

        frindAddFab.setOnClickListener {
            var intent = Intent(this, AddFrindActivity::class.java)
            startActivityForResult(intent, 100)
        }

        mainRecyclerView.layoutManager = layoutmanager
        var pres: SharedPreferences = getSharedPreferences("pres", Context.MODE_PRIVATE)
        val editor = pres.edit()

        val appPath: String = applicationContext.filesDir.absolutePath
        pultusORM = PultusORM("user.db", appPath)
        token = pres.getString("token", "")

        var toggle = ActionBarDrawerToggle(this, mainDrawer, mainToolbar, R.string.opentext, R.string.closetext)
        mainDrawer.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = false
        toggle.toolbarNavigationClickListener = View.OnClickListener {
            mainDrawer.openDrawer(GravityCompat.START)
        }
        toggle.setHomeAsUpIndicator(R.drawable.nav_drawer)

        bringList()

        var menu = nav_view.menu
//        var navPhone = menu.findItem(R.id.nav_phone)
//        var navEmail = menu.findItem(R.id.nav_email)
        var navLogout = menu.findItem(R.id.nav_logout)

        val header = nav_view.getHeaderView(0)
        var hearderText = header.findViewById(R.id.navName) as TextView
        var hearderImage = header.findViewById(R.id.navImage) as ImageView

        navLogout.setOnMenuItemClickListener {
            editor.remove("token")
            editor.commit()
            finish()
            var intent = Intent(this, SelectActivity::class.java)
            startActivity(intent)
            token = ""
            pultusORM.drop(User())
        }

        val userList: List<Any> = pultusORM.find(User())
        Log.e("suerList", userList.size.toString())
        if (userList.isNotEmpty()) {
            var user = userList[userList.size - 1] as User
            email = user.email
            name = user.name
            phone = user.phone
            img = user.profileImg

//            navEmail.title = email
//            navPhone.title = phone
            hearderText.text = name

            Glide.with(this).load(img).into(hearderImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode === Activity.RESULT_OK) {
            var bl = data!!.getStringExtra("frind")
            if (bl == "OK") {
                items.clear()
                Log.e("mainfrind", data.toString())
                bringList()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mainDrawer.closeDrawer(GravityCompat.START)
        return true
    }

    //서버연동
    fun bringList() {
        var adapter = RecyclerAdapter(items, adapterContext)
        mainRecyclerView.adapter = adapter
        val res: Call<FrindAdd> = RetrofitUtil.postService.FrindList(token)
        res.enqueue(object : Callback<FrindAdd> {

            override fun onResponse(call: Call<FrindAdd>?, response: Response<FrindAdd>?) {
                if (response!!.code() == 200) {
                    response.body()?.let {
                        Log.e("asdfdata", Gson().toJson(response.body()!!))

                        var obj = JSONObject(Gson().toJson(response.body()!!))
                        var title = obj.getJSONArray("list") as JSONArray
                        for (i in 0 until title.length()) {
                            var arr = title.getJSONObject(i) as JSONObject
                            var name = arr.getString("name") as String
                            var profile = arr.getString("profileImg") as String
                            var email = arr.getString("email") as String
                            var phone = arr.getString("phone") as String
                            var ischat = IsChat(email, token)
                            ischat.start()
                            ischat.join()
                            var chat = ischat.ischat()
                            Log.e("채팅add", chat.toString())
                            var chatmessge = ""
                            var chattime = ""
                            var chatcount = 0
                            var chatdata = loadNowData(email)

                            if (chatdata == "") {
                                chattime = "0"
                                chatmessge = "친구가 되었습니다."
                                Log.e("chatdata", chatdata)
                            } else {
                                var chatobj = JSONObject(chatdata)

                                if (chatobj.getString("time") == "") {
                                    chattime = chatobj.getString("resivetime")
                                } else {
                                    chattime = chatobj.getString("time")
                                }

                                if (chatobj.getString("message") == "") {
                                    chatmessge = chatobj.getString("resivemessage")
                                } else {
                                    chatmessge = chatobj.getString("message")
                                }

                                Log.e("chatdata!!", chatdata)
                            }
                            chatcount = lodeconut(email)
                            items.add(FrindItemData(name, email, phone, profile, chatmessge, chattime.format(Date()), token, chat, chatcount))
                        }
                        mainRecyclerView.adapter = adapter
                    }
                } else if (response.code() == 404) {
                    Log.e("retrofit404", response.message())
                }
            }

            override fun onFailure(call: Call<FrindAdd>?, t: Throwable?) {
                Log.e("retrofit Error!", t!!.message)
                Toast.makeText(applicationContext, "Sever Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.e("백드롭","뒤로감")
    }
    fun loadNowData(email: String): String? {
        val gson = Gson()
        Log.e("save", email)
        val pref = getSharedPreferences(email, Context.MODE_PRIVATE)
        val json = pref.getString("save", "")

        Log.e("items", json)
        var items: ArrayList<ChatSendData>?
        items = gson.fromJson<ArrayList<ChatSendData>>(json, object : TypeToken<ArrayList<ChatSendData>>() {}.type)

        if (items != null)
            test.addAll(items)

        if (json == "") {
            return ""
        } else {
            return Gson().toJson(test[test.size - 1])
        }
    }

    fun lodeconut(email: String) : Int {
        val pref = getSharedPreferences(email, Context.MODE_PRIVATE)
        var data = pref.getInt("count",0)
        Log.e("lodecount",data.toString())
        return data
    }
}



