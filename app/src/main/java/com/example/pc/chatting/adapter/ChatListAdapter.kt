package com.example.pc.chatting.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.pc.chatting.R
import com.example.pc.chatting.data.ChatData
import com.example.pc.chatting.data.ChatMyData
import com.mikhaellopez.circularimageview.CircularImageView

class ChatListAdapter(items: ArrayList<ChatMyData>,resiveitems : ArrayList<ChatData>,context: Context, layout: Int) : RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {

    private var inflater: LayoutInflater? = null
    var items: ArrayList<ChatMyData> = ArrayList()
    var adapterContext : Context? = null
    private var layout : Int = 0
    var resiveitems : ArrayList<ChatData> = ArrayList()

    init {
        this.items = items
        this.adapterContext = context
        this.layout = layout
        this.resiveitems = resiveitems
        inflater = LayoutInflater.from(context)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var data = items[position]
        var resive = resiveitems[position]

        holder!!.resivename.text = resive.name
        Glide.with(adapterContext).load(resive.image).into(holder!!.resiveimg)
        holder!!.resivemessge.text = resive.message
        holder!!.resivetime.text = resive.time

        holder!!.time.text = data.time
        holder!!.messge.text = data.message
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messge: TextView = itemView.findViewById(R.id.chatitemmessge) as TextView
        var time: TextView = itemView.findViewById(R.id.chatitemtime) as TextView
        var layout: LinearLayout = itemView.findViewById(R.id.chatlayout) as LinearLayout
        var resivename = itemView.findViewById(R.id.chatresivename) as TextView
        var resiveimg = itemView.findViewById(R.id.chatresiveimage) as CircularImageView
        var resivemessge = itemView.findViewById(R.id.chatresivemessge) as TextView
        var resivetime = itemView.findViewById(R.id.chatresivetime) as TextView
    }
}