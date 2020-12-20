package com.dmk.dindinnminiassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dmk.dindinnminiassignment.R
import com.dmk.dindinnminiassignment.model.Menu
import java.util.*

class MenuAdapter(private val context: Context) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    private var menuList: List<Menu> = ArrayList()
    fun refresh(menuList: List<Menu>) {
        this.menuList = menuList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.row_menu, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtName.text = " " + menuList[position].foodName
        holder.txtIngridients.text = " " + menuList[position].ingredients
        holder.btnAddToCart.text = " $" + menuList[position].price
        holder.btnAddToCart.setOnClickListener {
            //Add to Cart Logic
            //Persist user choice to local storage
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtName: TextView
        var txtIngridients: TextView
        var btnAddToCart: Button

        init {
            txtName = itemView.findViewById(R.id.txtName)
            txtIngridients = itemView.findViewById(R.id.txtIngridients)
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart)
        }
    }
}