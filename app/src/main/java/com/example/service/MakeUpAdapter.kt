package com.example.service
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class MakeUpAdapter(private val listener: UserMakeUp) : RecyclerView.Adapter<MakeUpAdapter.MakeUpViewHolder>() {
    private var makeupDataList = listOf<MakeupData>()

    class MakeUpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewEnterpriseName: TextView = itemView.findViewById(R.id.textViewEnterpriseName)
        val textViewUserName: TextView = itemView.findViewById(R.id.textViewUserName)
        val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress)
        val textViewServicesProvided: TextView = itemView.findViewById(R.id.textViewServicesProvided)
        val btnAdd: Button = itemView.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakeUpViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_makeup, parent, false)
        return MakeUpViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MakeUpViewHolder, position: Int) {
        val currentItem = makeupDataList[position]

        holder.textViewEnterpriseName.text = currentItem.field1
        holder.textViewUserName.text = currentItem.field2
        holder.textViewAddress.text = currentItem.field3
        holder.textViewServicesProvided.text = currentItem.field4
        holder.btnAdd.setOnClickListener {
            listener.onAddButtonClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return makeupDataList.size
    }

    fun setData(data: List<MakeupData>) {
        makeupDataList = data
        notifyDataSetChanged()
    }
}
