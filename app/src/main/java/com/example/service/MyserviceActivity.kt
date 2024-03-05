package com.example.service

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import serviceadapter

data class serviceData(
    val userId: String? = null,
    val enterpriseName: String? = null,
    val address: String? = null,
    val servicesProvided: String? = null,
    val serviceDescription: String? = null
    // Add other fields as needed
)
class MyserviceActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var serviceAdapter: serviceadapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    //    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myservice)
        recyclerView = findViewById(R.id.recyclerViewu)
        recyclerView.layoutManager = LinearLayoutManager(this)
        serviceAdapter = serviceadapter(this)
        recyclerView.adapter = serviceAdapter


        databaseReference = FirebaseDatabase.getInstance().reference

        fetchserviceData(databaseReference.child("makeUp"))
        fetchserviceData(databaseReference.child("electrician"))
        fetchserviceData(databaseReference.child("plumber"))
        fetchserviceData(databaseReference.child("barber"))
        fetchserviceData(databaseReference.child("housekeeping"))
        fetchserviceData(databaseReference.child("carpenter"))
    }
    private fun fetchserviceData(child: DatabaseReference) {
        // Listen for a single value event on the provided child reference
        child.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val serviceDataList = mutableListOf<serviceData>()
                auth = FirebaseAuth.getInstance()
                user = auth.currentUser!!
                val name = user.email.toString()

                for (snapshot in dataSnapshot.children) {
                    val userId = snapshot.key
                    val fields = snapshot.value as Map<String, String>
                    val usernameFromSnapshot = fields["userName"] ?: ""

                    if (usernameFromSnapshot == name) {
                        val field1 = fields["enterpriseName"] ?: ""
                        val field2 = fields["address"] ?: ""
                        val field3 = fields["servicesProvided"] ?: ""
                        val field4 = fields["serviceDescription"] ?: ""

                        val servicedata = serviceData(userId, field1, field2, field3, field4)
                        serviceDataList.add(servicedata)
                        Log.d("MyTag", "data added")
                    }
                }
                Log.d("MyTag", "Size of serviceDataList: ${serviceDataList.size}")
                // Update the adapter with fetched data
                serviceAdapter.setData(serviceDataList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.e("MyTag", "Database Error: ${databaseError.message}")
            }
        })
    }
}