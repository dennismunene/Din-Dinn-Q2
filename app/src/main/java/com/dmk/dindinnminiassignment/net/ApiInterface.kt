package com.dmk.dindinnminiassignment.net

import com.dmk.dindinnminiassignment.model.Category
import com.dmk.dindinnminiassignment.model.Menu
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @get:GET("/categories/")
    val categories: Call<List<Category?>?>?

    @GET("/category/")
    fun getCategoryMenu(@Query("id") id: Int): Call<List<Menu?>?>?
}