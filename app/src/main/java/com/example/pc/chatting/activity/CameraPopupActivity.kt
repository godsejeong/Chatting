package com.example.pc.chatting.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import com.example.pc.chatting.R
import kotlinx.android.synthetic.main.activity_camera_popup.*
import kotlinx.android.synthetic.main.activity_resiger.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.view.Window.FEATURE_NO_TITLE
import android.widget.LinearLayout
import android.widget.PopupWindow





/**
 * Created by pc on 2018-06-02.
 */
class CameraPopupActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_camera_popup)

        val popupView = layoutInflater.inflate(R.layout.activity_camera_popup, null)
        var mPopupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mPopupWindow.isFocusable = true
        //팝업 외의 화면 선택시 나가짐

        popupBasic.setOnClickListener {
            var intent = Intent(this,RegisterActivity::class.java)
            setResult(0,intent)
            intent.putExtra("img","basic")
            finish()
            Toast.makeText(applicationContext, "empty", Toast.LENGTH_SHORT).show()
        }
        popupCamera.setOnClickListener { camera() }
        popupGallery.setOnClickListener { gallery() }


    }

    fun camera(){
        var intent = Intent(this,RegisterActivity::class.java)
        setResult(1,intent)
        finish()
    }

    fun gallery(){
        var intent = Intent(this,RegisterActivity::class.java)
        setResult(2,intent)
        finish()
    }
}