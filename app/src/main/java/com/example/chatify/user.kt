package com.example.chatify
    // data class can't be used so we use normal class because firebase needs empty constructor
class user {
    var name : String? = null
    var email : String? = null
    var uid : String? = null
    constructor(){}
    constructor(name: String? ,email : String?, uid : String?){
        this.name = name
        this.email = email
        this.uid = uid
    }
}