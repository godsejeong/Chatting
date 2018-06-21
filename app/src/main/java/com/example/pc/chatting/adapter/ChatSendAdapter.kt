package com.example.pc.chatting.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.pc.chatting.R
import com.example.pc.chatting.data.ChatSendData
import com.mikhaellopez.circularimageview.CircularImageView

class ChatSendAdapter(items: ArrayList<ChatSendData>, context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var senditems: ArrayList<ChatSendData> = ArrayList()
    private var adapterContext : Context? = null

    init {
        this.senditems = items
        this.adapterContext = context
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        if (senditems[position].viewtype == 0) {
            return 0
        } else {
            return 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view : View? = null
        return when {
            viewType === 0 -> {
                view = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.chat_send_item, parent, false)
                SendHolder(view)
            }
            viewType === 1 -> {
                view = LayoutInflater.from(parent!!.context)
                        .inflate(R.layout.chat_resive_item, parent, false)
                ReciveHolder(view)
            }
            else -> ReciveHolder(view!!)
        }
    }

    override fun getItemCount(): Int {
        return senditems!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var data = senditems[position]
        if (holder is SendHolder){
            holder.time.text = data.time
            holder.messge.text = data.message
        }else{
            (holder as ReciveHolder)
            holder.resivename.text = data.name
//            holder.resiveimg.setImageURI(Uri.parse(data.image))
            Log.e("sendadapter",data.image)
            Glide.with(adapterContext).load(data.image).into(holder.resiveimg)
            holder.resivemessge.text = data.resivemessage
            holder.resivetime.text = data.resivetime
        }

    }

     class SendHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messge: TextView = itemView.findViewById(R.id.chatitemmessge) as TextView
        var time: TextView = itemView.findViewById(R.id.chatitemtime) as TextView
    }

     class ReciveHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var resivename = itemView.findViewById(R.id.chatresivename) as TextView
        var resiveimg = itemView.findViewById(R.id.chatresiveimage) as CircularImageView
        var resivemessge = itemView.findViewById(R.id.chatresivemessge) as TextView
        var resivetime = itemView.findViewById(R.id.chatresivetime) as TextView
    }
}