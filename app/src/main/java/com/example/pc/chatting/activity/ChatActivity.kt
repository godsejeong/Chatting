package com.example.pc.chatting.activity

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import com.example.pc.chatting.R
import com.example.pc.chatting.data.User
import com.example.pc.chatting.util.RetrofitUtil
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import kotlinx.android.synthetic.main.activity_chat.*
import ninja.sakib.pultusorm.core.PultusORM
//import android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import org.json.JSONException
import org.json.JSONObject
import org.json.simple.parser.JSONParser


class ChatActivity : AppCompatActivity() {
    lateinit var pultusORM: PultusORM
    var name: String = ""
    var img: String = ""
    var username: String = ""
    var message: String = ""
    var myname: String = ""
    var room: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSupportActionBar(chatToolbar)

        var soket = IO.socket(RetrofitUtil.URL)
        val appPath: String = applicationContext.filesDir.absolutePath
        pultusORM = PultusORM("user.db", appPath)

        name = intent.getStringExtra("name")
        img = intent.getStringExtra("img")
        room = intent.getStringExtra("room")
        Log.e("chatname", name)
        supportActionBar!!.title = name

        soket.on("receive message", OnMessage)
        soket.connect()
        val userList: List<Any> = pultusORM.find(User())
        if (userList.isNotEmpty()) {
            var user = userList[userList.size - 1] as User
            myname = user.name
        }
        chatSendBtn.setOnClickListener {
            val data = JSONObject()
            try {
                data.put("name", myname)
                data.put("index", chatText.text)
                data.put("room", room)
                soket.emit("send message",data)

            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }

    }

    private var OnMessage = Emitter.Listener { args ->
        this.runOnUiThread {
            var data = args[0].toString()
            Log.e("asdfjson",data)
            val json = JSONParser().parse(data) as JSONObject
            username = json .getString("name")
            message = json .getString("index")
            Log.e("username",username)
            Log.e("message",message)
        }
    }
}
