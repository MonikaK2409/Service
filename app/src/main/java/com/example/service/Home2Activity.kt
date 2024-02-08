package com.example.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Home2Activity :  ComponentActivity(){
    private lateinit var auth: FirebaseAuth
    private lateinit var button: Button
    private lateinit var text: TextView
    private lateinit var user: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home2)
        auth= FirebaseAuth.getInstance()
        button=findViewById(R.id.loggout)
        text=findViewById(R.id.texty)
        user = auth.currentUser!!
        if(user==null){
            val intent = Intent(applicationContext,Login::class.java)

            // Start the LoginActivity
            startActivity(intent)
        }
        else{
            text.text=user.email
        }
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(applicationContext,Login::class.java)
                startActivity(intent)
            }
        } )
    }
}