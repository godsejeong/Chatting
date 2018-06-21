package com.example.pc.chatting.data

class ChatSendData(message: String?, time: String?, name: String?, resivemessage: String?, resivetime: String?, image: String?,viewtype : Int) {
    var message: String = ""
    var time: String = ""

    var name : String = ""
    var resivemessage : String = ""
    var resivetime : String = ""
    var image : String = ""

    var viewtype : Int = 0
    init {
        this.message = message!!
        this.time = time!!
        this.name = name!!
        this.resivemessage = resivemessage!!
        this.resivetime = resivetime!!
        this.image = image!!
        this.viewtype = viewtype
    }
}