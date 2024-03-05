package com.example.service

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RatingActivity : ComponentActivity() {
    private lateinit var ratingBar: RatingBar
    private lateinit var btnSubmitRating: Button
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rating)
        ratingBar = findViewById(R.id.ratingBar)
        btnSubmitRating = findViewById(R.id.btnSubmitRating)
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        btnSubmitRating.setOnClickListener {
            submitRating()
            val intent= Intent(applicationContext,HomeActivity::class.java)
            Toast.makeText(
                this@RatingActivity ,
                "Thankyou for Rating :)",
                Toast.LENGTH_SHORT,
            ).show()
            startActivity(intent)
        }
    }
    private fun submitRating() {
        val userName = auth.currentUser?.email // Get the username from Firebase Auth
        val ref = databaseReference.child("requests") // Replace with the actual provider's name
        val rating = ratingBar.rating

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (requestSnapshot in dataSnapshot.children) {
                    val username = requestSnapshot.child("userName").getValue(String::class.java)
                    val providerName =
                        requestSnapshot.child("providerName").getValue(String::class.java)

                    if (username == userName && providerName != null) {
                        // Do something with the provider name

                        Log.d("MyTag", "Provider Name for username userName: $providerName")
                        val userMap = mapOf(
                            "username" to userName,
                            "providername" to providerName,
                            "ratings" to rating
                        )
                        writeratings(userMap)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.e("MyTag", "Database Error: ${databaseError.message}")
            }
        })
    }

    fun writeratings(userData: Map<String, Any?>) {
        Log.d("MyTag", "writeratings function called")
        val userRef = databaseReference.child("ratings").push()
        userRef.setValue(userData)
    }
}
