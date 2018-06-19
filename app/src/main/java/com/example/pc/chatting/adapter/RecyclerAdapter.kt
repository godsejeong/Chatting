package com.example.pc.chatting.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pc.chatting.R
import com.example.pc.chatting.data.FrindGetData
import com.example.pc.chatting.data.FrindItemData
import com.mikhaellopez.circularimageview.CircularImageView


class RecyclerAdapter(frinditems : ArrayList<FrindItemData>,context : Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var frinditems : ArrayList<FrindItemData> = ArrayList()
    var adapterContext : Context? = null

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
        var items = frinditems[position]
        holder!!.name.text = items.name
        Glide.with(adapterContext).load(items.img).into(holder.img)
        holder!!.messge.text = items.messge
        holder!!.time.text = items.time
        holder!!.notice.text = items.notice.toString()

        holder.itemView.setOnClickListener {
            Toast.makeText(adapterContext,items.name + "님을 클릭하셨습니다.",Toast.LENGTH_SHORT).show()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.frindItemName) as TextView
        var img : CircularImageView = itemView.findViewById(R.id.frindItemProfile) as CircularImageView
        var messge : TextView = itemView.findViewById(R.id.frindItemMessge) as TextView
        var time : TextView = itemView.findViewById(R.id.frindItemTime) as TextView
        var notice : TextView = itemView.findViewById(R.id.frindItemNotice) as TextView

    }

}
