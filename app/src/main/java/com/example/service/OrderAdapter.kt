package com.example.service

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private var orderList = mutableListOf<Order>()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewCustomerName: TextView = itemView.findViewById(R.id.textViewCustomerName)
        val textViewServiceName:TextView= itemView.findViewById(R.id.textViewServiceName)
        val textViewStatus:TextView= itemView.findViewById(R.id.textViewStatus)
        val acceptButton: Button = itemView.findViewById(R.id.acceptButton)
        val rejectButton: Button = itemView.findViewById(R.id.rejectButton)
        // Add more views as needed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.oreders_list, parent, false)
        return OrderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentItem = orderList[position]

        holder.textViewCustomerName.text = currentItem.userName
        holder.textViewServiceName.text = currentItem.services
        holder.textViewStatus.text = currentItem.requestStatus
        // Bind more data as needed
        holder.acceptButton.setOnClickListener {
            val databaseReference = FirebaseDatabase.getInstance().reference.child("requests")

            val query = databaseReference.orderByChild("providerName").equalTo(currentItem.providerName)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        // Assuming there's only one matching request, you can update its status
                        val requestId = snapshot.key // Assuming the unique identifier is the key of the snapshot

                        // Update requestStatus to "Rejected"
                        val snapshotOrder = snapshot.getValue(Order::class.java)
                        if (snapshotOrder != null && snapshotOrder.providerName == currentItem.providerName && snapshotOrder.services == currentItem.services) {
                            snapshot.ref.child("requestStatus").setValue("Accepted")
                                .addOnSuccessListener {
                                    Log.d("Updation", "Success")
                                }
                                .addOnFailureListener { exception ->
                                    // Handle failure
                                    // You can log the error or show an error message to the user
                                    Log.e("Act", "Error updating request status: $exception")
                                }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors
                    Log.e("Act", "Database error: $databaseError")
                }
            })

        }

        holder.rejectButton.setOnClickListener {
            val databaseReference = FirebaseDatabase.getInstance().reference.child("requests")

            val query = databaseReference.orderByChild("providerName").equalTo(currentItem.providerName)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        // Assuming there's only one matching request, you can update its status
                        val requestId = snapshot.key // Assuming the unique identifier is the key of the snapshot

                        // Update requestStatus to "Rejected"
                        val snapshotOrder = snapshot.getValue(Order::class.java)
                        if (snapshotOrder != null && snapshotOrder.providerName == currentItem.providerName && snapshotOrder.services == currentItem.services) {
                            snapshot.ref.child("requestStatus").setValue("Rejected")
                                .addOnSuccessListener {
                                    Log.d("Updation", "Success")
                                }
                                .addOnFailureListener { exception ->
                                    // Handle failure
                                    // You can log the error or show an error message to the user
                                    Log.e("Act", "Error updating request status: $exception")
                                }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors
                    Log.e("Act", "Database error: $databaseError")
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    fun setData(data: List<Order>) {
        orderList.clear()
        orderList.addAll(data)
        notifyDataSetChanged()
    }
}


