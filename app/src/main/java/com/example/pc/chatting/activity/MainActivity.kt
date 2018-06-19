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
import com.example.pc.chatting.R
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pc.chatting.R.layout.nav_header_layout
import com.example.pc.chatting.util.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.pc.chatting.data.SignUp
import com.example.pc.chatting.data.Token
import com.example.pc.chatting.data.User
import com.google.gson.Gson
import kotlinx.android.synthetic.main.app_bar_layout.*
import kotlinx.android.synthetic.main.nav_header_layout.*
import ninja.sakib.pultusorm.core.PultusORM
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import org.w3c.dom.Text


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var pultusORM : PultusORM
    var token: String = ""
    var img: String = ""
    var email: String = ""
    var name: String = ""
    var phone: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("starttoken",token)
        setSupportActionBar(mainToolbar)

        var pres: SharedPreferences = getSharedPreferences("pres", Context.MODE_PRIVATE)
        val editor = pres.edit()
        var toggle = ActionBarDrawerToggle(this,mainDrawer,mainToolbar,R.string.opentext,R.string.closetext)
        mainDrawer.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = false
        toggle.toolbarNavigationClickListener = View.OnClickListener{
            mainDrawer.openDrawer(GravityCompat.START)
        }
        toggle.setHomeAsUpIndicator(R.drawable.drawericon)

        var menu = nav_view.menu
        var navPhone = menu.findItem(R.id.nav_phone)
        var navEmail = menu.findItem(R.id.nav_email)
        var navLogout = menu.findItem(R.id.nav_logout)
        val header = nav_view.getHeaderView(0)
        var hearderText = header.findViewById(R.id.navName) as TextView
        var hearderImage = header.findViewById(R.id.navImage) as ImageView

        val appPath: String = applicationContext.filesDir.absolutePath
        pultusORM = PultusORM("user.db", appPath)

        token = pres.getString("token", "")
        Log.e("token", token)

        val userList: List<Any> = pultusORM.find(User())
          Log.e("suerList", userList.size.toString())

        navLogout.setOnMenuItemClickListener{
            editor.remove("token")
            editor.commit()
            finish()
            var intent = Intent(this,SelectActivity::class.java)
            startActivity(intent)
            token = ""
            pultusORM.drop(User())

        }

        if(userList.isNotEmpty()){
            var user= userList[userList.size - 1] as User
            email = user.email
            name = user.name
            phone = user.phone
            img = user.profileImg

            navEmail.title = email
            navPhone.title =  phone
            hearderText.text = name

            Glide.with(this).load(img).into(hearderImage)
            }

        frindAddFab.setOnClickListener {
            var intent = Intent(this,AddFrindActivity::class.java)
            startActivityForResult(intent,100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode === Activity.RESULT_OK){

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        Log.e("click","clickclick")
        when(item.itemId){

            R.id.nav_logout -> {
            }
        }
        mainDrawer.closeDrawer(GravityCompat.START)
        return true
    }
    //서버연동

}
