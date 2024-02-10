package com.example.service

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProvElectrician :  ComponentActivity() {
    private lateinit var eNameEditText: EditText
    private lateinit var eServicesEditText: EditText
    private lateinit var eDescEditText: EditText
    private lateinit var eAddressEditText: EditText
    private lateinit var eContactEditText: EditText
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prov_make_up)
        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance().reference.child("electrician")
        auth= FirebaseAuth.getInstance()
        // Initialize EditText fields
        user=auth.currentUser!!
        eNameEditText = findViewById(R.id.eName)
        eServicesEditText = findViewById(R.id.eServices)
        eDescEditText = findViewById(R.id.eDesc)
        eContactEditText = findViewById(R.id.eContact)
        eAddressEditText = findViewById(R.id.eAddress)
    }
    fun saveData(view: View) {
        val eName = eNameEditText.text.toString()
        val eServices = eServicesEditText.text.toString()
        val eDesc = eDescEditText.text.toString()
        val eAdr = eAddressEditText.text.toString()
        val eCont = eContactEditText.text.toString()
        // Check if any of the fields are empty
        if (eName.isEmpty() || eServices.isEmpty() || eDesc.isEmpty()) {
            // Show error message or handle empty fields as needed
            return
        }

        val userMap = mapOf(
            "userName" to user.email,
            "enterpriseName" to eName,
            "contactNumber" to eCont,
            "address" to eAdr,
            "servicesProvided" to eServices,
            "serviceDescription" to eDesc
        )
        writeNewUser(userMap)
    }

    private fun writeNewUser(userMap: Map<String, String?>) {
        Log.d("MyTag", "writeNewUser function called")
        val userRef = database.push()
        userRef.setValue(userMap)
            .addOnSuccessListener {
                Toast.makeText(this,"Successfully Added your data", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext,Home2Activity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this,"Sorry..An error occurred", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext,Home2Activity::class.java)
                startActivity(intent)
            }
    }
}