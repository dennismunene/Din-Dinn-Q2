package com.dmk.dindinnminiassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dmk.dindinnminiassignment.R

class CartAdapter(private val context: Context) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var totalItems = 5
    fun mockRemoveItem() {
        Toast.makeText(context, "Item Removed!", Toast.LENGTH_SHORT).show()
        if (totalItems != 0) totalItems--
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.row_cart_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btnRemove.setOnClickListener { mockRemoveItem() }
    }

    override fun getItemCount(): Int {
        return totalItems
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtName: TextView
        var txtPrice: TextView
        var btnRemove: ImageButton

        init {
            txtName = itemView.findViewById(R.id.txtName)
            txtPrice = itemView.findViewById(R.id.txtPrice)
            btnRemove = itemView.findViewById(R.id.btnRemove)
        }
    }
}