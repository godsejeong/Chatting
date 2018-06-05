package com.example.pc.chatting

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import okhttp3.MultipartBody


/**
 * Created by pc on 2018-04-04.
 */

data class Signin(val email : String,val passwd : String,var token : String)

data class SignUp(val email : String,val passwd : String, val name : String, val phone : Int, val img : MultipartBody.Part)




