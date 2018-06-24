package com.example.pc.chatting.data

<<<<<<< HEAD
class FrindItemData(name : String,email : String,phone : String,img : String,messge: String, time: String,token : String,ischat : Boolean ,notice: Int){
=======
class FrindItemData(name : String,email : String,phone : String,img : String,messge: String, time: String, notice: Int,isChat : Boolean,token : String){
>>>>>>> 56ce1c9bd2daf79ab9fc3818deaeb58d390ea6a1
    var messge : String = ""
    var time : String = ""
    var notice : Int = 0
    var name : String = ""
    var img : String = ""
    var email : String = ""
    var phone : String = ""
    var token : String = ""
<<<<<<< HEAD
    var ischat : Boolean? = null
=======
    var isChat : Boolean? = null
>>>>>>> 56ce1c9bd2daf79ab9fc3818deaeb58d390ea6a1
    init {
        this.messge = messge
        this.time = time
        this.notice = notice
        this.name = name
        this.email = email
        this.img = img
        this.phone = phone
        this.token = token
<<<<<<< HEAD
        this.ischat = ischat
=======
        this.isChat = isChat
>>>>>>> 56ce1c9bd2daf79ab9fc3818deaeb58d390ea6a1
    }
}