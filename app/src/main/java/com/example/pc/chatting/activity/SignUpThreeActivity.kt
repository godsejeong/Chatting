package com.example.pc.chatting.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pc.chatting.R
import com.example.pc.chatting.data.SignUp
import com.example.pc.chatting.util.RetrofitUtil
import kotlinx.android.synthetic.main.activity_sign_up_three.*
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class SignUpThreeActivity : AppCompatActivity() , EasyPermissions.PermissionCallbacks{
    var image: File? = null
    var path: String? = null
    var fileuri: Uri? = null
    var file: File? = null
    var uri: Uri? = null
    var i: Int = 0
    var Name : String = ""
    var Phone : String = ""
    var Email : String = ""
    var Passwd : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_three)
        Email = intent.getStringExtra("email")
        Passwd = intent.getStringExtra("passwd")
        Name = intent.getStringExtra("name")
        Phone = intent.getStringExtra("phone")

        signUpThreeFinish.setOnClickListener{
            signup()
        }

        signUpThreeBack.setOnClickListener {
            finish()
        }

        ProfileLayout.setOnClickListener{
            var intent = Intent(this, CameraPopupActivity::class.java)
            startActivityForResult(intent,1)
        }

    }

    fun camera(){
        if(EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
            var cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            Log.e("cameraUri", fileuri.toString())
            file = getOutputMediaFileUri()
            fileuri = FileProvider.getUriForFile(this,"com.example.pc.provider",file!!)
            cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,fileuri)
            startActivityForResult(cameraIntent, 100)
        } else {
            EasyPermissions.requestPermissions(this,"사진을 찍으려면 권한이 필요합니다",200, android.Manifest.permission.CAMERA)
        }
    }

    fun gallery(){
        if(EasyPermissions.hasPermissions(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent,200)
        } else {
            EasyPermissions.requestPermissions(this,"파일을 읽으려면 권한이 필요합니다",300, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == 0) {
            BasicProfileSetting()
                var img = data?.getStringExtra("img")
                if (img == "basic") {//팝업밖의 레이아웃을 눌렀을때 이미지 변경을 방지
                    //setdata
                    signUpProfile.setImageResource(R.drawable.emptyimg)
                    signUpCamera.visibility = View.GONE
                }
            }
        if (resultCode == 1) {
            camera()
        }
        if (resultCode == 2) {
            gallery()
        }

        if (requestCode == 200 && resultCode === Activity.RESULT_OK) {
            uri = data!!.data
            Glide.with(this).load(uri).into(signUpProfile)
            signUpCamera.visibility = View.GONE
            file = File(getRealPathFromURIPath(uri!!, this))
            Log.e("uripath", uri.toString())
            //ImageCrop(false)
        }

        if (requestCode == 100 && resultCode === Activity.RESULT_OK) {
            //RESULT_OK -> 카메라를 실제로 찍었는지, 취소로 나갔는지
            Log.e("camera", "camera")
            uri = fileuri
            Glide.with(this).load(uri).into(signUpProfile)
            signUpCamera.visibility = View.GONE
            Log.e("requestcamerauri", uri.toString())
        }
    }

    fun signup(){
        var loginintent = Intent(this,LoginActivity::class.java)
        val res : Call<SignUp> = RetrofitUtil.postService.SignUp(
                Email,
                Passwd,
                Name,
                Phone,
                RetrofitUtil.createMultipartBody(file!!,"profileImg")
        )

        res.enqueue(object : Callback<SignUp> {
            override fun onResponse(call: Call<SignUp>?, response: Response<SignUp>?) {
                Log.e("register",response!!.code().toString())
                Log.e("register_Message",response.message())
                if(response!!.code() == 200){
                    response.body()?.let {
                        Toast.makeText(applicationContext, "회원가입이 정상적으로 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        startActivity(loginintent)
                    }
                }else if(response.code() == 409){
                    Toast.makeText(applicationContext,"이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show()
                }else if(response.code() == 400){
                    Toast.makeText(applicationContext,"Server Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignUp>?, t: Throwable?) {
                Log.e("retrofit Error", t!!.message)
            }
        })
    }

    //camera_저장경로 설정
    private fun getOutputMediaFileUri() : File? {
        if (isExternalStorageAvailable()) {
            val imagePath = "IMG_" + SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Chatting")
            storageDir!!.mkdir()
            image = File.createTempFile(imagePath, ".jpg", storageDir)
            path = image!!.absolutePath
            Log.e("imagepath", image.toString())
            return image
        }

        return image
    }

    private fun isExternalStorageAvailable() : Boolean{
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    //drawable -> file저장
    fun BasicProfileSetting(){
        var drawable: Drawable = getDrawable(R.drawable.emptyimg)
        var bitmap = (drawable as BitmapDrawable).bitmap
        var filepath = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"Chatting")//파일경로
        filepath.mkdir()
        file= File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"Chatting/basicimg.jpg")//파일생성
        //메모리 관리를 위해 파일명 고정
        Log.e("basicuri", file.toString())
        var stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()
    }

    //uri->filepath 변환
    private fun getRealPathFromURIPath(contentURI: Uri, activity: Activity): String {
        val cursor = activity.contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx)
        }
    }

    //esaypermission_override
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
        if(requestCode == 300) {
            gallery()
        }

        if(requestCode == 200){
            camera()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {
        Toast.makeText(this,"권한이 없습니다", Toast.LENGTH_SHORT).show()
    }

}


