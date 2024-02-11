package com.example.service

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class BarberAdapter(private val listener: UserBarber) : RecyclerView.Adapter<BarberAdapter.BarberViewHolder>() {
    private var barberDataList = listOf<BarberData>()

    class BarberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewEnterpriseName: TextView = itemView.findViewById(R.id.textViewEnterpriseName)
        val textViewUserName: TextView = itemView.findViewById(R.id.textViewUserName)
        val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress)
        val textViewServicesProvided: TextView = itemView.findViewById(R.id.textViewServicesProvided)
        val btnAdd: Button = itemView.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarberViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_makeup, parent, false)
        return BarberViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BarberViewHolder, position: Int) {
        val currentItem = barberDataList[position]

        holder.textViewEnterpriseName.text = currentItem.field1
        holder.textViewUserName.text = currentItem.field2
        holder.textViewAddress.text = currentItem.field3
        holder.textViewServicesProvided.text = currentItem.field4
        holder.btnAdd.setOnClickListener {
            listener.onAddButtonClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return barberDataList.size
    }

    fun setData(data: List<BarberData>) {
        barberDataList = data
        notifyDataSetChanged()
    }
}