package com.example.service

import androidx.appcompat.app.AppCompatActivity
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

data class Order(
    val userId: String? = null,
    val services: String? = null,  // Update field1 to services
    val userName: String? = null,  // Update field2 to userName
    val providerName: String? = null,  // Update field3 to providerName

)

class CartActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var providerName: String
    private lateinit var user: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart)
        auth= FirebaseAuth.getInstance()
        // Initialize EditText fields
        user=auth.currentUser!!
        providerName = user.email.toString()
        Log.d("CartActivity", "Provider Name: $providerName")

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recyclerView7)
        recyclerView.layoutManager = LinearLayoutManager(this)
        orderAdapter = OrderAdapter()
        recyclerView.adapter = orderAdapter

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("requests")

        // Fetch orders data for the specific provider
        fetchOrdersData()
    }

    private fun fetchOrdersData() {
        databaseReference.orderByChild("providerName").equalTo(providerName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val orderList = mutableListOf<Order>()

                    for (snapshot in dataSnapshot.children) {
                        val order = snapshot.getValue(Order::class.java)
                        order?.let {
                            orderList.add(it)
                        }
                    }

                    // Update the adapter with the retrieved orders data
                    orderAdapter.setData(orderList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors
                }
            })
    }
}