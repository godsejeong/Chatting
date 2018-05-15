package com.example.pc.chatting.Interceptor

import com.example.pc.chatting.preferences.CookieSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by pc on 2018-05-15.
 */
class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        // 가져온 chain으로 부터 빌더 객체를 생성
        var builder : Request.Builder? = chain!!.request().newBuilder()

        // CookieSharedPreferences에 저장되어있는 쿠키 값을 가져옴
//        var cookies = CookieSharedPreferences.getHashSet(
//                CookieSharedPreferences.COOKIE_SHARED_PREFERENCES_KEY,
//                HashSet<String>()
//        ) as HashSet



    }

}