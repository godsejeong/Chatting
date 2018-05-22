package com.example.pc.chatting

import android.content.SharedPreferences
import com.google.gson.annotations.SerializedName
import com.orm.SugarRecord;
/**
 * Created by pc on 2018-05-15.
 */
class Token(var token : String) : SugarRecord() {
    @SerializedName("data")
    var tokens : String? = null

    init {
        tokens = token
    }
}