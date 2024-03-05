package com.example.service

import android.annotation.SuppressLint
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

data class myOrder(
    val userName:String?=null,
    val providerName:String?=null,
    val services:String?=null,
    val requestStatus:String?=null
)
class ordersOfConsumer : ComponentActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var myorderAdapter: MyOrderAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var userName:String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders_of_consumer)
        setContentView(R.layout.cart)
        auth= FirebaseAuth.getInstance()
        // Initialize EditText fields
        user=auth.currentUser!!
        userName = user.email.toString()
        Log.d("CartActivity", "User Name: $userName")

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recyclerView7)
        recyclerView.layoutManager = LinearLayoutManager(this)
        myorderAdapter = MyOrderAdapter()
        recyclerView.adapter = myorderAdapter

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("requests")

        // Fetch orders data for the specific provider
        fetchOrdersData()
    }
    private fun fetchOrdersData() {
        databaseReference.orderByChild("userName").equalTo(userName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val orderList = mutableListOf<myOrder>()

                    for (snapshot in dataSnapshot.children) {
                        val order = snapshot.getValue(myOrder::class.java)
                        order?.let {
                            orderList.add(it)
                        }
                    }

                    // Update the adapter with the retrieved orders data
                    myorderAdapter.setData(orderList)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors
                }
            })
    }
}