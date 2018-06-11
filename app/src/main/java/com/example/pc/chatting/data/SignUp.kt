package com.example.pc.chatting.data

import okhttp3.MultipartBody

class SignUp(val email : String , val passwd : String , val name : String , val phone : Int , val img : MultipartBody.Part)
