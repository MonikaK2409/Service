import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.service.MyserviceActivity
import com.example.service.R

import com.example.service.serviceData

class serviceadapter(private val listener: MyserviceActivity) : RecyclerView.Adapter<serviceadapter.serviceViewHolder>() {
    var serviceDataList = mutableListOf<serviceData>()

    class serviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewEnterpriseName: TextView = itemView.findViewById(R.id.textViewEnterpriseName)
        val textViewdescription: TextView = itemView.findViewById(R.id.textViewdescription)
        val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress)
        val textViewServicesProvided: TextView = itemView.findViewById(R.id.textViewServicesProvided)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): serviceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.serve, parent, false)
        return serviceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: serviceViewHolder, position: Int) {
        val currentItem = serviceDataList[position]
        Log.d("MyTag", "onBindViewHolder position: $position")
        holder.textViewEnterpriseName.text = currentItem.enterpriseName
        holder.textViewAddress.text = currentItem.address
        holder.textViewdescription.text = currentItem.servicesProvided
        holder.textViewServicesProvided.text = currentItem.serviceDescription

    }

    override fun getItemCount(): Int {
        return serviceDataList.size
    }

    fun setData(data: List<serviceData>) {

        serviceDataList.addAll(data)
        notifyDataSetChanged()
    }
}



