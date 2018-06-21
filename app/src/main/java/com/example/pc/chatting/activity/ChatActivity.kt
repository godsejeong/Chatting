package com.example.pc.chatting.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.pc.chatting.R
import com.example.pc.chatting.adapter.ChatSendAdapter
import com.example.pc.chatting.data.ChatSendData
import com.example.pc.chatting.data.User
import com.example.pc.chatting.util.RetrofitUtil
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat.*
import ninja.sakib.pultusorm.core.PultusORM
//import android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.google.gson.reflect.TypeToken
import android.content.SharedPreferences




class ChatActivity : AppCompatActivity() {
    lateinit var pultusORM: PultusORM
    var name: String = ""
    var img: String = ""
    var username: String = ""
    var message: String = ""
    var myname: String = ""
    var room: String = ""
    var senditems: ArrayList<ChatSendData> = ArrayList()
    private lateinit var sendadapter: ChatSendAdapter
    var layoutmanager = LinearLayoutManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSupportActionBar(chatToolbar)
        var soket = IO.socket(RetrofitUtil.URL)
        val appPath: String = applicationContext.filesDir.absolutePath

        chatRecyclerView.layoutManager = layoutmanager

        pultusORM = PultusORM("user.db", appPath)
        loadNowData()
        sendadapter = ChatSendAdapter(senditems,this)
        chatRecyclerView.adapter = sendadapter

        name = intent.getStringExtra("name")
        img = intent.getStringExtra("img")
        room = intent.getStringExtra("room")
        Log.e("chatname", room)
        supportActionBar!!.title = name

        soket.emit("join room", name, room)
        soket.on("receive message", OnMessage)
        soket.connect()

        val userList: List<Any> = pultusORM.find(User())

        if (userList.isNotEmpty()) {
            var user = userList[userList.size - 1] as User
            myname = user.name
        }

        chatSendBtn.setOnClickListener {
            Log.e("chatonclick", myname)
            var date = SimpleDateFormat("a hh:mm").format(Date())
            soket.emit("send message", myname, chatText.text, room)
            senditems.add(ChatSendData(chatText.text.toString(), date,"","","","",0))
            sendadapter.notifyDataSetChanged()
            saveNowData()
        }
    }

    private var OnMessage = Emitter.Listener { args ->
        this.runOnUiThread {
            var data = args[0] as JSONObject
            var date = SimpleDateFormat("a hh:mm").format(Date())
            Log.e("asdfjson",img)
            username = data.getString("name")
            message = data.getString("index")
            senditems.add(ChatSendData("","",username,message,date,img,1))
            sendadapter.notifyDataSetChanged()
            saveNowData()
            Log.e("username",img)
        }
    }

    fun loadNowData() {
        val gson = Gson()
        val pref = getSharedPreferences("chat", Context.MODE_PRIVATE)
        val json = pref.getString("save", "")
        var items: ArrayList<ChatSendData>?
        items = gson.fromJson<ArrayList<ChatSendData>>(json, object : TypeToken<ArrayList<ChatSendData>>(){}.type)
        if (items != null) senditems.addAll(items)
    }


    fun saveNowData() { //items 안의 내용이 저장됨
        val pref = getSharedPreferences("chat", Context.MODE_PRIVATE)
        val editor = pref.edit()
        val json = Gson().toJson(senditems)
        editor.putString("save", json)
        editor.commit()
    }
}
