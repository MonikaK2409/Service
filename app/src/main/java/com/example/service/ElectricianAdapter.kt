package com.example.service

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class ElectricianAdapter(private val listener: userElectrician) : RecyclerView.Adapter<ElectricianAdapter.ElectricianViewHolder>() {
    private var electricianDataList = listOf<ElectricianData>()

    class ElectricianViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewEnterpriseName: TextView = itemView.findViewById(R.id.textViewEnterpriseName)
        val textViewUserName: TextView = itemView.findViewById(R.id.textViewUserName)
        val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress)
        val textViewServicesProvided: TextView = itemView.findViewById(R.id.textViewServicesProvided)
        val btnAdd: Button = itemView.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectricianViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_makeup, parent, false)
        return ElectricianViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ElectricianViewHolder, position: Int) {
        val currentItem = electricianDataList[position]

        holder.textViewEnterpriseName.text = currentItem.field1
        holder.textViewUserName.text = currentItem.field2
        holder.textViewAddress.text = currentItem.field3
        holder.textViewServicesProvided.text = currentItem.field4
        holder.btnAdd.setOnClickListener {
            listener.onAddButtonClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return electricianDataList.size
    }

    fun setData(data: List<ElectricianData>) {
        electricianDataList = data
        notifyDataSetChanged()
    }
}
