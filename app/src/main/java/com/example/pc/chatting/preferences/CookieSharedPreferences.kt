package com.example.pc.chatting.preferences

import android.content.Context
import android.app.Activity



/**
 * Created by pc on 2018-05-15.
 */
class CookieSharedPreferences(context : Context) {

    val

    //CookieSharedPreferences 참조 key값
    val COOKIE_SHARED_PREFERENCES_KEY = "new.devetude.www.cookie"

    //싱글톤 모델 객체화
    private var cookieSharedPreferences: CookieSharedPreferences? = null

    fun getInstanceOf(c: Context): CookieSharedPreferences {
        if (cookieSharedPreferences == null) {
            cookieSharedPreferences = CookieSharedPreferences(c)
        }

        return cookieSharedPreferences as CookieSharedPreferences
    }



}