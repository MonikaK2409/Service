package com.example.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

data class ElectricianData(
    val userId: String? = null,
    val field1: String? = null,
    val field2: String? = null,
    val field3: String? = null,
    val field4: String? = null
    // Add other fields as needed
)

class userElectrician : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var electricianAdapter: ElectricianAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_electrician)
        recyclerView = findViewById(R.id.recyclerView4)
        recyclerView.layoutManager = LinearLayoutManager(this)
        electricianAdapter = ElectricianAdapter(this)
        recyclerView.adapter = electricianAdapter

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("electrician")

        // Fetch makeup data from Firebase
        fetchMakeupData()
    }
    private fun fetchMakeupData() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val electricianDataList = mutableListOf<ElectricianData>()

                for (snapshot in dataSnapshot.children) {
                    val userId = snapshot.key
                    val fields = snapshot.value as Map<String, String>

                    val field1 = fields["enterpriseName"] ?: ""
                    val field2 = fields["userName"] ?: ""
                    val field3 = fields["address"] ?: ""
                    val field4 = fields["servicesProvided"] ?: ""

                    val makeupData = ElectricianData(userId,field1, field2, field3, field4)
                    electricianDataList.add(makeupData)
                }

                // Update the adapter with fetched data
                electricianAdapter.setData(electricianDataList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })
    }
    fun onAddButtonClick(electricianData: ElectricianData) {
        // Handle button click here
        // plumberData contains the details of the clicked item
        // You can perform any action based on the clicked item's details
        databaseReference = FirebaseDatabase.getInstance().reference.child("requests")
        auth= FirebaseAuth.getInstance()
        // Initialize EditText fields
        user=auth.currentUser!!
        val userMap = mapOf(
            "userName" to user.email,
            "providerName" to electricianData.field2
        )
        writeNewUser(userMap)
    }
    private fun writeNewUser(userMap: Map<String, String?>) {
        Log.d("MyTag", "writeNewUser function called")
        val userRef = databaseReference.push()
        userRef.setValue(userMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully Added your data", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Sorry..An error occurred", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            }
    }
}