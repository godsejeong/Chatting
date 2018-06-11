package com.example.pc.chatting.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pc.chatting.R
import com.example.pc.chatting.util.RetrofitUtil
import kotlinx.android.synthetic.main.activity_resiger.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern
import android.provider.MediaStore
import android.content.Intent
import android.net.Uri
import android.os.Environment
import java.io.File
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.FileProvider
import android.view.View
import com.example.pc.chatting.data.SignUp
import pub.devrel.easypermissions.EasyPermissions
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
open class RegisterActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    var id: String = ""
    var passwd: String = ""
    var name: String = ""
    var email: String = ""
    var phone: String = ""
    var emptycheck: Boolean = false
    var uri: Uri? = null
    var file: File? = null
    var path: String? = null
    var fileuri: Uri? = null
    var image: File? = null
    var i: Int = 0


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resiger)
        userPassword.hint = "Password"
        userName.hint =  "User Name"
        userEmail.hint = "User Email"
        userPhone.hint = "Phone Number"
        userProfile.setOnClickListener{
            var intent = Intent(this,CameraPopupActivity::class.java)
            startActivityForResult(intent,1)
        }

        backBtn.setOnClickListener{
            finish()
        }

        completeBtn.setOnClickListener {
            passwd = userPassword.text.toString()
            name = userName.text.toString()
            email = userEmail.text.toString()
            phone = userPhone.text.toString()

            var emails = ("^[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$")//email 정규식
            var passwords = ("^(?=.*?[A-Za-z])(?=.*?[0-9]).{8,}$")//passwd 정규식
            var phones = ("^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-[0-9]{3,4}-[0-9]{4}$") //phone number 정규식
            val emailm = Pattern.matches(emails,userEmail.text)
            val phonem = Pattern.matches(phones,userPhone.text)
            val passwordm = Pattern.matches(passwords,userPassword.text)
            //정규식 변환

            Log.e("i", emailm.toString())

            if(!passwordm){
                emptycheck = true
                userPassword.error = "8자 이상 입력하고 숫자와 영문자를 적어도 하나 이상 입력해주세요"
                userPassword.requestFocus()
            }

            if(!emailm){
                emptycheck = true
                userEmail.error = "이메일 형식에 맞게 입력하세요"
                userEmail.requestFocus()
            }

            if(!phonem){
                emptycheck = true
                userPhone.error = "휴대폰번호 형식에 맞게 입력하세요" +
                        "ex)01000000000"
                userPhone.requestFocus()
            }

            if(email.isEmpty()) {
                emptycheck = true
                userEmail.error = "이메일을 입력하십시오"
                userEmail.requestFocus()
            }else{
                emptycheck = false
            }
            if(passwd.isEmpty()){
                emptycheck = true
                userPassword.error = "비밀번호를 입력하십시오"
                userPassword.requestFocus()
            }else{
                emptycheck = false
            }
            if(name.isEmpty()){
                emptycheck = true
                userName.error = "이름을 입력하십시오"
                userName.requestFocus()
            }else{
                emptycheck = false
            }
            if(phone.isEmpty()){
                emptycheck = true
                userPhone.error = "전화번호를 입력하십시오"
                userPhone.requestFocus()
            }else{
                emptycheck = false
            }

            if(!emptycheck){
                Log.e("signupcheak","ok!!")
                signup()
            }
        }
    }

    fun camera(){
        if(EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
            var cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            Log.e("cameraUri", fileuri.toString())
            file = getOutputMediaFileUri()
            fileuri = FileProvider.getUriForFile(this,"com.example.pc.provider",file)
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
            i++
            if (i == 1) {
                BasicProfileSetting()
            } else {//static

                var img = data?.getStringExtra("img")
                if (img == "basic") {//팝업밖의 레이아웃을 눌렀을때 이미지 변경을 방지
                    //setdata
                    Profile.setImageResource(R.drawable.emptyimg)
                    cameraImg.visibility = View.GONE
                }
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
                Profile.setImageURI(uri)
                cameraImg.visibility = View.GONE
                file = File(getRealPathFromURIPath(uri!!, this))
                Log.e("uripath", uri.toString())
                //ImageCrop(false)
            }

            if (requestCode == 100 && resultCode === Activity.RESULT_OK) {
                //RESULT_OK -> 카메라를 실제로 찍었는지, 취소로 나갔는지
                Log.e("camera", "camera")
                uri = fileuri
                Profile.setImageURI(fileuri)
                cameraImg.visibility = View.GONE
                Log.e("requestcamerauri", uri.toString())
                //ImageCrop(true)
//            file = image
//            MediaScannerConnection.scanFile(this,arrayOf<String>(uri!!.path),null,
//                    object : MediaScannerConnection.OnScanCompletedListener{
//                        override fun onScanCompleted(path: String, uri: Uri) {}
//                    })

            }

//        if(requestCode == 10 && resultCode === Activity.RESULT_OK){
//            //Log.e("crop",data!!.data.toString())
//            var bundle = data!!.extras
//            var bitmap : Bitmap = bundle.getParcelable("data")
//            Profile.setImageBitmap(bitmap)
//            cameraImg.visibility = View.GONE
//            var bl = data?.getStringExtra("img")
//            if(bl == "camera") {
//                file = File(getRealPathFromURIPath(uri!!,this))
//                Log.e("uripath","겔리리 들옴")
//            }else if(bl == "gallery"){
//                file = image
//                Log.e("uripath","카메라 들옴")
//            }
//            Log.e("uripath", uri.toString())
//        }
    }


    //서버 통신
    fun signup(){
        val res : Call<SignUp> = RetrofitUtil.postService.SignUp(
                email,
                passwd,
                name,
                Integer.parseInt(userPhone.text.toString()),
                RetrofitUtil.createMultipartBody(file!!,"profileImg")
        )

        res.enqueue(object : Callback<SignUp>{
            override fun onResponse(call: Call<SignUp>?, response: Response<SignUp>?) {
                Log.e("register",response!!.code().toString())
                Log.e("register_Message",response!!.message())
                if(response!!.code() == 200){
                    response.body()?.let {
                        Toast.makeText(applicationContext, "회원가입이 정상적으로 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }else if(response!!.code() == 409){
                    Toast.makeText(applicationContext,"이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show()
                }else if(response!!.code() == 400){
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

            return null
        }
    private fun isExternalStorageAvailable() : Boolean{
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
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
        Toast.makeText(this,"권한이 없습니다",Toast.LENGTH_SHORT).show()
    }

//###########################################이미지 크롭##########################################################
//    fun ImageCrop(a : Boolean){
//        this.grantUriPermission("com.android.camera", uri,
//                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        var CropIntent = Intent("com.android.camera.action.CROP")
//        CropIntent.setDataAndType(uri,"image/*")
//
//        var list = packageManager.queryIntentActivities(intent, 0)
//        var size = list.size
//        if(size == 0){
//            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show()
//            return
//        } else {
//            Toast.makeText(this, "용량이 큰 사진의 경우 시간이 오래 걸릴 수 있습니다.", Toast.LENGTH_SHORT).show()
//            CropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            CropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//            CropIntent.putExtra("output", uri)
//            CropIntent.putExtra("outputX", 640) // crop한 이미지의 x축 크기 (integer)
//            CropIntent.putExtra("outputY", 480) // crop한 이미지의 y축 크기 (integer)
//            CropIntent.putExtra("aspectX", 4) // crop 박스의 x축 비율 (integer)
//            CropIntent.putExtra("aspectY", 3) // crop 박스의 y축 비율 (integer)
//            CropIntent.putExtra("scale", true)
//            if(a) {
//                CropIntent.putExtra("camera", "camera")
//            }else{
//                CropIntent.putExtra("camera", "gallery")
//            }
//            CropIntent.putExtra("return-data", true)
//        }
//        startActivityForResult(CropIntent,10)
//    }
}