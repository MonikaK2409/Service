package com.example.service

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

data class CartItem(
    val userName:String? = null,
    val enterpriseName: String? = null,
    val providerName: String? = null,
    val address: String? = null,
    val services: String? = null
    // Add other fields as needed
)
class UserCart : ComponentActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var userName:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cart)
        auth = FirebaseAuth.getInstance()
        user=auth.currentUser!!
        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("cart")
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(this)
        recyclerView.adapter = cartAdapter
        userName=user.email.toString()
        Log.d("CartActivity", "User Name: $userName")
        // Initialize Firebase authentication


        // Get current user
        fetchCartItems()
    }
    private fun fetchCartItems() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val cartDataList = mutableListOf<CartItem>()

                for (snapshot in dataSnapshot.children) {
                    val userId = snapshot.key
                    val fields = snapshot.value as Map<String, String>
                    val userName = fields["userName"]?:" "
                    val field1 = fields["enterpriseName"] ?: ""
                    val field2 = fields["providerName"] ?: ""
                    val field3 = fields["address"] ?: ""
                    val field4 = fields["services"] ?: ""

                    val cartData = CartItem(userName,field1, field2, field3, field4)
                    cartDataList.add(cartData)
                }

                // Update the adapter with fetched data
                cartAdapter.setData(cartDataList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })
    }

    fun onBookButtonClick(cartsItem: CartItem) {
        // Get reference to requests node
        databaseReference = FirebaseDatabase.getInstance().reference.child("requests")
        auth= FirebaseAuth.getInstance()
        // Initialize EditText fields
        user=auth.currentUser!!
        val userMap = mapOf(
            "userName" to user.email,
            "providerName" to cartsItem.providerName,
            "services" to cartsItem.services
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

    companion object {
        private const val TAG = "CartActivity"
    }
}