package com.example.pc.chatting.adapter

import android.view.View
import android.view.ViewGroup
import com.example.pc.chatting.data.FrindListData
import android.view.LayoutInflater
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.*
import com.bumptech.glide.Glide
import com.example.pc.chatting.R
import com.example.pc.chatting.R.id.itemImg
import com.example.pc.chatting.data.FrindAdd
import com.example.pc.chatting.util.RetrofitUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.frind_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FrindListAdapter(context : Context,token : String,data : ArrayList<FrindListData>,layout : Int) : BaseAdapter(){
    private var inflater: LayoutInflater? = null
    private var data: ArrayList<FrindListData>? = null
    private var layout: Int = 0
    private var token = ""
    private var context : Context? = null
    init{
        this.inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        this.data = data
        this.layout = layout
        this.token = token
        this.context = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View? = inflater?.inflate(R.layout.frind_list_item,parent,false)

        var name = view?.findViewById (R.id.itemName) as TextView
        var img = view?.findViewById (R.id.itemImg) as ImageView
        var Btn = view?.findViewById (R.id.itemBtn) as Button
        var frindData: FrindListData = getItem(position)
        name.text = frindData.name
        Glide.with(context).load(frindData.img).into(view.itemImg)
        Btn.setOnClickListener{
            val res: Call<FrindAdd> = RetrofitUtil.postService.Useradd(token,name.text.toString())
            res.enqueue(object : Callback<FrindAdd> {
                override fun onResponse(call: Call<FrindAdd>?, response: Response<FrindAdd>?) {
                    if(response!!.code() == 200){
                        response.body()?.let {
                            Log.e("frindlog",token)
                            Log.e("Frindadd", Gson().toJson(response.body()!!))
                        }
                    }
                }

                override fun onFailure(call: Call<FrindAdd>?, t: Throwable?) {
                    Log.e("retrofit Error", t!!.message)
                }
            })
        }
        return view!!
    }

    override fun getItem(position: Int): FrindListData {
        return data!!.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data!!.size
    }



}