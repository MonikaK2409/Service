package com.example.service

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startpage)

        // Find the button by its ID
        val button: Button = findViewById(R.id.button)

        // Set a click listener on the button
        button.setOnClickListener {
            // Create an Intent to start the Login activity
            val intent = Intent(this, Login::class.java)

            // Start the LoginActivity
            startActivity(intent)
        }
    }
}
