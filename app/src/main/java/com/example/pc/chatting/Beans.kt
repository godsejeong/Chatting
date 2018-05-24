package com.example.pc.chatting


/**
 * Created by pc on 2018-04-04.
 */

data class Signin(val id : String,val passwd : String)

data class SignUp(val id : String,val passwd : String, val name : String, val email : String, val phone : Int)


