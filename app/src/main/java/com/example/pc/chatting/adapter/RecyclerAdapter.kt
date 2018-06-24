package com.example.pc.chatting.adapter

import android.content.Context
import android.content.Intent
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
import com.example.pc.chatting.data.FrindAdd
import com.example.pc.chatting.data.FrindItemData
import com.example.pc.chatting.data.RoomId
import com.example.pc.chatting.util.CreateRoom
import com.example.pc.chatting.util.RetrofitUtil
import com.example.pc.chatting.util.RoomCheak
import com.github.nkzawa.socketio.client.IO
import com.mikhaellopez.circularimageview.CircularImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class RecyclerAdapter(frinditems: ArrayList<FrindItemData>, context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var frinditems: ArrayList<FrindItemData> = ArrayList()
    var adapterContext: Context? = null

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
        var returnroom = ""

        if (!items.ischat!!) {
            Log.e("roominfomation",false.toString())

            val res: Call<RoomId> = RetrofitUtil.postService.FrindRoom(items.email,items.token)

                res.enqueue(object : Callback<RoomId> {

                    override fun onResponse(call: Call<RoomId>?, response: Response<RoomId>?) {
                        if (response!!.code() == 200) {
                            returnroom = response.body()!!.roomID
                            Log.e("returnString1234",returnroom)
                        } else {
                            Log.e("adapterserver", "에러")
                        }
                    }

                    override fun onFailure(call: Call<RoomId>?, t: Throwable?) {
                        Log.e("adapterservererror", t!!.message)
                    }
                })

//            var createroom = CreateRoom(items.email, items.token)
//            createroom.start()
//            createroom.join()
//            returnroom = createroom.room()
        } else if (items.ischat!!) {
            Log.e("roominfomation",true.toString())
            var roomcheak = RoomCheak(items.email, items.token)
            roomcheak.start()
            roomcheak.join()
            returnroom = roomcheak.cheak()
        }

        Log.e("roominfomation", returnroom)
        holder.itemView.setOnClickListener {
            intent.putExtra("room", returnroom)
            intent.putExtra("name", items.name)
            intent.putExtra("img", items.img)
            intent.putExtra("email", items.email)
            adapterContext!!.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.frindItemName) as TextView
        var img: CircularImageView = itemView.findViewById(R.id.frindItemProfile) as CircularImageView
        var messge: TextView = itemView.findViewById(R.id.frindItemMessge) as TextView
        var time: TextView = itemView.findViewById(R.id.frindItemTime) as TextView
        var notice: TextView = itemView.findViewById(R.id.frindItemNotice) as TextView

    }

}
