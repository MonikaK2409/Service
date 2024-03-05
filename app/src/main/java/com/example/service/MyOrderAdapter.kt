package com.example.service

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class MyOrderAdapter : RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder>(){
    private var orderList = mutableListOf<myOrder>()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    class MyOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewCustomerName: TextView = itemView.findViewById(R.id.textViewCustomerName)
        val textViewServiceName: TextView = itemView.findViewById(R.id.textViewServiceName)
        val textViewStatus: TextView = itemView.findViewById(R.id.textViewStatus)
        val ratingsButton: Button = itemView.findViewById(R.id.ratingsButton)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.show_orders_for_consumers, parent, false)
        return MyOrderViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyOrderViewHolder, position: Int){
        val currentItem = orderList[position]

        holder.textViewCustomerName.text = currentItem.userName
        holder.textViewServiceName.text = currentItem.services
        holder.textViewStatus.text = currentItem.requestStatus
        holder.ratingsButton.setOnClickListener{
            //Monika..write your code here
        }
    }
    override fun getItemCount(): Int {
        return orderList.size
    }

    fun setData(data: MutableList<myOrder>) {
        orderList.clear()
        orderList.addAll(data)
        notifyDataSetChanged()
    }
}
