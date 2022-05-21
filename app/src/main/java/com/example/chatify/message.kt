package com.example.chatify

class message {
    var message : String? = null
    var idSender : String? = null
    constructor(){}
    constructor(message : String?, idSender : String?){
        this.message = message
        this.idSender = idSender
    }

}