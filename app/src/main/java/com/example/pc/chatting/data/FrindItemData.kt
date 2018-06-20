package com.example.pc.chatting.data

class FrindItemData(name : String,img : String, messge: String, time: String, notice: Int){
    var messge : String = ""
    var time : String = ""
    var notice : Int = 0
    var name : String = ""
    var img : String = ""

    init {
        this.messge = messge
        this.time = time
        this.notice = notice
        this.name = name
        this.img = img
    }
}