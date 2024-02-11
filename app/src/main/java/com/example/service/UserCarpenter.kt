package com.example.service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

data class CarpenterData(
    val userId: String? = null,
    val field1: String? = null,
    val field2: String? = null,
    val field3: String? = null,
    val field4: String? = null
    // Add other fields as needed
)
class UserCarpenter : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var carpenterAdapter: CarpenterAdapter
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_carpenter)
        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recyclerView3)
        recyclerView.layoutManager = LinearLayoutManager(this)
        carpenterAdapter = CarpenterAdapter()
        recyclerView.adapter = carpenterAdapter

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("carpenter")

        // Fetch makeup data from Firebase
        fetchMakeupData()
    }
    private fun fetchMakeupData() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val carpenterDataList = mutableListOf<CarpenterData>()

                for (snapshot in dataSnapshot.children) {
                    val userId = snapshot.key
                    val fields = snapshot.value as Map<String, String>

                    val field1 = fields["enterpriseName"] ?: ""
                    val field2 = fields["userName"] ?: ""
                    val field3 = fields["address"] ?: ""
                    val field4 = fields["servicesProvided"] ?: ""

                    val carpenterData = CarpenterData(userId,field1, field2, field3, field4)
                    carpenterDataList.add(carpenterData)
                }

                // Update the adapter with fetched data
                carpenterAdapter.setData(carpenterDataList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })
    }
}