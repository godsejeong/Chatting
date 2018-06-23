package com.example.pc.chatting.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pc.chatting.R
import com.example.pc.chatting.activity.ChatActivity
import com.example.pc.chatting.data.*
import com.example.pc.chatting.util.RetrofitUtil
import com.github.nkzawa.socketio.client.IO
import com.mikhaellopez.circularimageview.CircularImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class RecyclerAdapter(frinditems: ArrayList<FrindItemData>, context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var frinditems: ArrayList<FrindItemData> = ArrayList()
    var adapterContext: Context? = null
    var isbool : Boolean? = null
    init {
        this.frinditems = frinditems
        this.adapterContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.frind_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return frinditems.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var intent = Intent(adapterContext, ChatActivity::class.java)
        var items = frinditems[position]
        holder!!.name.text = items.name
        Glide.with(adapterContext).load(items.img).into(holder.img)
        holder!!.messge.text = items.messge
        holder!!.time.text = items.time
        holder!!.notice.text = items.notice.toString()
        var returnString = ""
        var isbool: Boolean? = null




//        Log.e("testtest", ischat(items.email,items.token).toString())

//        if (!isbool!!) {
//            Log.e("cheak", "안녕")
//            returnString = Room.room(items.email, items.token)
//            dataSave(items.name, returnString)
//        }

        Log.e("recyclerroom", returnString)

        holder.itemView.setOnClickListener {
            intent.putExtra("room", returnString)
            intent.putExtra("name", items.name)
            intent.putExtra("img", items.img)
            adapterContext!!.startActivity(intent)
        }
    }

    fun dataSave(name: String, room: String) {
        var pres: SharedPreferences = adapterContext!!.getSharedPreferences("pres", Context.MODE_PRIVATE)
        var editer: SharedPreferences.Editor = pres.edit()
        editer.putString(name, room)
        editer.commit()
    }

    fun ischat(email : String,token : String) : Boolean{
        val res: Call<IsChatData> = RetrofitUtil.postService.Ischat(token,email)
        res.enqueue(object : Callback<IsChatData> {
                override fun onResponse(call: Call<IsChatData>?, response: Response<IsChatData>?) {
                    Log.e("isfrindlist", response!!.code().toString())
                    if (response!!.code() == 200) {
                        isbool = response.body()!!.isChat
                        Log.e("returnString1234", isbool.toString())
                    } else if (response!!.code() == 404) {
                        Log.e("adapterserver", "에러")
                    }
                }

                override fun onFailure(call: Call<IsChatData>?, t: Throwable?) {
                    Log.e("adapterservererror", t!!.message)
                }
            })

        return isbool!!
    }
//
//    class Room {
//        companion object Room {
//            var returnString = ""
//            fun room(email: String, token: String): String {
//                val res: Call<RoomId> = RetrofitUtil.postService.FrindRoom(email, token)
//
//                res.enqueue(object : Callback<RoomId> {
//
//                    override fun onResponse(call: Call<RoomId>?, response: Response<RoomId>?) {
//                        if (response!!.code() == 200) {
//                            returnString = response.body()!!.roomID
//                            Log.e("returnString1234", returnString)
//                        } else {
//                            Log.e("adapterserver", "에러")
//                        }
//                    }
//
//                    override fun onFailure(call: Call<RoomId>?, t: Throwable?) {
//                        Log.e("adapterservererror", t!!.message)
//                    }
//                })
//                return returnString
//            }
//        }
//    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.frindItemName) as TextView
        var img: CircularImageView = itemView.findViewById(R.id.frindItemProfile) as CircularImageView
        var messge: TextView = itemView.findViewById(R.id.frindItemMessge) as TextView
        var time: TextView = itemView.findViewById(R.id.frindItemTime) as TextView
        var notice: TextView = itemView.findViewById(R.id.frindItemNotice) as TextView

    }

}
