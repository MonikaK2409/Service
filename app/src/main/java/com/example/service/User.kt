package com.example.service

data class User(val username: String? = null, val email: String? = null,val service: String? = null)
{
    // Null default values create a no-argument default constructor, which is needed
   val User:String
    val E_mail:String
    val MyService:String
    init
    {
        this.User = username.toString()
        this.E_mail = email.toString()
        this.MyService = service.toString()
    }
}