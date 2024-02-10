package com.example.service
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class HousekeepingAdapter : RecyclerView.Adapter<HousekeepingAdapter.HousekeepingViewHolder>() {
    private var housekeepingDataList = listOf<HousekeepingData>()

    class HousekeepingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewEnterpriseName: TextView = itemView.findViewById(R.id.textViewEnterpriseName)
        val textViewUserName: TextView = itemView.findViewById(R.id.textViewUserName)
        val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress)
        val textViewServicesProvided: TextView = itemView.findViewById(R.id.textViewServicesProvided)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HousekeepingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_makeup, parent, false)
        return HousekeepingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HousekeepingViewHolder, position: Int) {
        val currentItem = housekeepingDataList[position]

        holder.textViewEnterpriseName.text = currentItem.field1
        holder.textViewUserName.text = currentItem.field2
        holder.textViewAddress.text = currentItem.field3
        holder.textViewServicesProvided.text = currentItem.field4
    }

    override fun getItemCount(): Int {
        return housekeepingDataList.size
    }

    fun setData(data: List<HousekeepingData>) {
        housekeepingDataList = data
        notifyDataSetChanged()
    }
}