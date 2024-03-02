package com.example.service

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
        // Bind more data as needed
        holder.acceptButton.setOnClickListener {
            // Implement accept functionality
            // For example, navigate to a new activity to show extra information
        }

        holder.rejectButton.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val databaseReference = database.getReference("requests")

            // Remove the rejected service from the database
            val query = databaseReference.orderByChild("providerName").equalTo(currentItem.providerName)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val snapshotOrder = snapshot.getValue(Order::class.java)
                        if (snapshotOrder != null && snapshotOrder.services == currentItem.services && snapshotOrder.userName == currentItem.userName) {
                            snapshot.ref.removeValue()
                            Log.d("Deletion", "Deleted item: ${currentItem.userId}")
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle errors
                }
            })

            // Remove the rejected service from the list and notify the adapter
            orderList.removeAt(position)
            notifyItemRemoved(position)

            // Implement reject functionality
            // For example, send a message to the consumer
            sendMessageToConsumer(currentItem.userId)
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
    private fun sendMessageToConsumer(userId: String?) {
        // Implement message sending functionality
        // You can use Firebase Cloud Messaging (FCM) or any other messaging system
    }
}


