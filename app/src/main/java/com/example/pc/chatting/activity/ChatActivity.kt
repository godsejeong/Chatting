package com.example.pc.chatting.activity

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Adapter
import com.example.pc.chatting.R
import com.example.pc.chatting.adapter.ChatListAdapter
import com.example.pc.chatting.data.ChatData
import com.example.pc.chatting.data.ChatMyData
import com.example.pc.chatting.data.User
import com.example.pc.chatting.util.RetrofitUtil
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.content_drawer.*
import ninja.sakib.pultusorm.core.PultusORM
//import android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import org.json.JSONException
import org.json.JSONObject
import org.json.simple.parser.JSONParser
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChatActivity : AppCompatActivity() {
    lateinit var pultusORM: PultusORM
    var name: String = ""
    var img: String = ""
    var username: String = ""
    var message: String = ""
    var myname: String = ""
    var room: String = ""
    var items: ArrayList<ChatMyData> = ArrayList()
    var senditems : ArrayList<ChatData> = ArrayList()
    lateinit var adapter: ChatListAdapter
    var layoutmanager = LinearLayoutManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSupportActionBar(chatToolbar)
        var soket = IO.socket(RetrofitUtil.URL)
        val appPath: String = applicationContext.filesDir.absolutePath

        chatListView.layoutManager = layoutmanager

        pultusORM = PultusORM("user.db", appPath)

        adapter = ChatListAdapter(items,senditems, this, R.layout.chat_item)
        chatListView.adapter = adapter

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
            items.add(ChatMyData(chatText.text.toString(), date))
            adapter.notifyDataSetChanged()
        }
    }

    private var OnMessage = Emitter.Listener { args ->
        adapter = ChatListAdapter(items,senditems, this, R.layout.chat_item)
        this.runOnUiThread {
            var data = args[0] as JSONObject
            var date = SimpleDateFormat("a hh:mm").format(Date())
            Log.e("asdfjson", data.toString())
            username = data.getString("name")
            message = data.getString("index")
            senditems.add(ChatData(username,message,date,img))
            adapter.notifyDataSetChanged()
            Log.e("username", username)
            Log.e("message", message)
        }
    }
}
