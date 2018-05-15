package com.example.pc.chatting

import com.google.gson.annotations.SerializedName

/**
 * Created by pc on 2018-04-04.
 */
class Repo{
    lateinit var id : String
    lateinit var name : String
    lateinit var full_name : String
}

data class Signin(var id : String, var password : String)
data class Sighup(var id : String,var password : String,var name : String,var phonenumber : String)
