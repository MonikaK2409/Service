package com.example.service

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private var orderList = listOf<Order>()

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewCustomerName: TextView = itemView.findViewById(R.id.textViewCustomerName)
        val textViewServiceName:TextView= itemView.findViewById(R.id.textViewServiceName)
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
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    fun setData(data: List<Order>) {
        orderList = data
        notifyDataSetChanged()
    }
}
