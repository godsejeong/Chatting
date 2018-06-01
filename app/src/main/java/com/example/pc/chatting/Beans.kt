package com.example.pc.chatting


/**
 * Created by pc on 2018-04-04.
 */

data class Signin(val email : String,val passwd : String,var token : String)

data class SignUp(val email : String,val passwd : String, val name : String, val phone : Int)


data class DB(val DB : String)




