package com.example.service

data class User(val username: String? = null, val email: String? = null,val service: String? = null) {
    // Null default values create a no-argument default constructor, which is needed
    val user:String
    val emaill:String
    val servicee:String
    init
    {
        this.user = username.toString()
        this.emaill = email.toString()
        this.servicee = service.toString()
    }
}