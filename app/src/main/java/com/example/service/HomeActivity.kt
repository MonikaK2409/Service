package com.example.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.service.R.id.text3

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HomeActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var button: Button
    private lateinit var text: TextView
    private lateinit var user: FirebaseUser



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        auth= FirebaseAuth.getInstance()
        button=findViewById(R.id.logout)
        text=findViewById(text3)
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
    fun cartclicked(view: View) {
        val intent = Intent(applicationContext,UserCart::class.java)
        startActivity(intent)
    }
    fun makeupfun(view: View) {
        val intent = Intent(applicationContext,UserMakeUp::class.java)
        startActivity(intent)
    }

    fun plumberfun(view: View) {
        val intent = Intent(applicationContext,UserPlumber::class.java)
        startActivity(intent)
    }

    fun electricianfun(view: View) {
        val intent = Intent(applicationContext,userElectrician::class.java)
        startActivity(intent)
    }
    fun carpenterfun(view: View) {
        val intent = Intent(applicationContext,UserCarpenter::class.java)
        startActivity(intent)
    }
    fun homekeepingfun(view: View) {
        val intent = Intent(applicationContext,UserHousekeeping::class.java)
        startActivity(intent)
    }
    fun barberfun(view: View) {
        val intent = Intent(applicationContext,UserBarber::class.java)
        startActivity(intent)
    }

    fun ordersbuttonclicked(view: View) {
        val intent = Intent(applicationContext,ordersOfConsumer::class.java)
        startActivity(intent)
    }
}