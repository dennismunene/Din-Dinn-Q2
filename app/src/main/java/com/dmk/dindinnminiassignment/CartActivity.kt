package com.dmk.dindinnminiassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmk.dindinnminiassignment.adapter.CartAdapter

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val rcCart = findViewById<RecyclerView>(R.id.rcCart)
        rcCart.layoutManager = LinearLayoutManager(this)
        rcCart.adapter = CartAdapter(applicationContext)
    }
}