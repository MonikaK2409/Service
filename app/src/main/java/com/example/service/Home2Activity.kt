package com.example.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
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



    fun provMakeup(view: View) {
        val intent = Intent(applicationContext,ProvMakeUp::class.java)
        startActivity(intent)
    }

    fun provPlumber(view: View) {
        val intent = Intent(applicationContext,ProvPlumber::class.java)
        startActivity(intent)
    }
    fun provElec(view: View) {
        val intent = Intent(applicationContext,ProvElectrician::class.java)
        startActivity(intent)
    }
    fun provCarp(view: View) {
        val intent = Intent(applicationContext,ProvCarpenter::class.java)
        startActivity(intent)
    }
    fun provHouse(view: View) {
        val intent = Intent(applicationContext,ProvHousekeeping::class.java)
        startActivity(intent)
    }
    fun provBarber(view: View) {
        val intent = Intent(applicationContext,ProvBarber::class.java)
        startActivity(intent)
    }

    fun gotoinfo(view: View) {
        val intent = Intent(applicationContext,CartActivity::class.java)
        startActivity(intent)
    }

    fun show(view: View) {
        val intent = Intent(applicationContext,MyserviceActivity::class.java)
        startActivity(intent)
    }
}