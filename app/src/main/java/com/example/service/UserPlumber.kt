package com.example.service

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

data class PlumberData(
    val userId: String? = null,
    val field1: String? = null,
    val field2: String? = null,
    val field3: String? = null,
    val field4: String? = null
    // Add other fields as needed
)
class UserPlumber : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var plumberAdapter: PlumberAdapter
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_plumber)

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        plumberAdapter = PlumberAdapter()
        recyclerView.adapter = plumberAdapter

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("plumber")

        // Fetch makeup data from Firebase
        fetchMakeupData()
    }

    private fun fetchMakeupData() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val plumberDataList = mutableListOf<PlumberData>()

                for (snapshot in dataSnapshot.children) {
                    val userId = snapshot.key
                    val fields = snapshot.value as Map<String, String>

                    val field1 = fields["enterpriseName"] ?: ""
                    val field2 = fields["userName"] ?: ""
                    val field3 = fields["address"] ?: ""
                    val field4 = fields["servicesProvided"] ?: ""

                    val makeupData = PlumberData(userId,field1, field2, field3, field4)
                    plumberDataList.add(makeupData)
                }

                // Update the adapter with fetched data
                plumberAdapter.setData(plumberDataList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })
    }

    fun sendMessage(view: View) {

    }
}