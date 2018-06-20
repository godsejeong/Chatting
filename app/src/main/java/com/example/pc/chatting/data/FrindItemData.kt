package com.example.pc.chatting.data

class FrindItemData(name : String,email : String,phone : String,img : String,messge: String, time: String, notice: Int){
    var messge : String = ""
    var time : String = ""
    var notice : Int = 0
    var name : String = ""
    var img : String = ""
    var email : String = ""
    var phone : String = ""
    init {
        this.messge = messge
        this.time = time
        this.notice = notice
        this.name = name
        this.email = email
        this.img = img
        this.phone = phone
    }
}