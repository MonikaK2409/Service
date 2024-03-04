package com.example.service

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartAdapter(private val listener: UserCart) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var cartItemList = mutableListOf<CartItem>()
    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewEnterpriseName: TextView = itemView.findViewById(R.id.textViewEnterpriseName1)
        val textViewUserName: TextView = itemView.findViewById(R.id.textViewUserName1)
        val textViewAddress: TextView = itemView.findViewById(R.id.textViewAddress1)
        val textViewServicesProvided: TextView = itemView.findViewById(R.id.textViewServicesProvided1)
        val btnBook: Button = itemView.findViewById(R.id.bookButton)
        val btnRemove: Button = itemView.findViewById(R.id.removeButton)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.show_user_cart, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = cartItemList[position]

        holder.textViewEnterpriseName.text = currentItem.enterpriseName
        holder.textViewUserName.text = currentItem.providerName
        holder.textViewAddress.text = currentItem.address
        holder.textViewServicesProvided.text = currentItem.services

        holder.btnBook.setOnClickListener {
            listener.onBookButtonClick(currentItem)
        }
        holder.btnRemove.setOnClickListener {

                val database = FirebaseDatabase.getInstance()
                val databaseReference = database.getReference("cart")

                // Remove the rejected service from the database
                val query = databaseReference.orderByChild("userName").equalTo(currentItem.userName)
                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot in dataSnapshot.children) {
                            val snapshotOrder = snapshot.getValue(CartItem::class.java)
                            if (snapshotOrder != null && snapshotOrder.enterpriseName == currentItem.enterpriseName && snapshotOrder.providerName == currentItem.providerName && snapshotOrder.address == currentItem.address && snapshotOrder.services == currentItem.services) {
                                snapshot.ref.removeValue()
                                cartItemList.removeAt(position)
                                notifyItemRemoved(position)
                                Log.d("Deletion", "Deleted item: ${currentItem.userName}")
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle errors
                        Log.d("Deletion", "deletion agilla: ${currentItem.providerName}")
                    }
                })

                // Remove the rejected service from the list and notify the adapter


            }
        }


    override fun getItemCount(): Int {
        return cartItemList.size
    }

    fun setData(data: List<CartItem>) {
        cartItemList.clear()
        cartItemList.addAll(data)
        notifyDataSetChanged()
    }

    interface CartItemListener {
        fun onRemoveButtonClick(cartItem: CartItem)
    }


}
