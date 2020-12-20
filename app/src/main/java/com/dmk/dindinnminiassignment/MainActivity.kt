package com.dmk.dindinnminiassignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.dmk.dindinnminiassignment.adapter.MenuAdapter
import com.dmk.dindinnminiassignment.model.Category
import com.dmk.dindinnminiassignment.model.Menu
import com.dmk.dindinnminiassignment.net.ApiClient
import com.dmk.dindinnminiassignment.net.ApiInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    var apiService: ApiInterface? = null;
    var menuAdapter: MenuAdapter? = null;
    var rcMenuList: RecyclerView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        apiService = ApiClient.client?.create(
            ApiInterface::class.java
        );
        //Init Views
        val fab = findViewById<FloatingActionButton>(R.id.fab);
        fab.bringToFront()
        fab.setOnClickListener {
            startActivity(Intent(applicationContext,CartActivity::class.java))
        }



        val imageSlider = findViewById<ImageSlider>(R.id.slider)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout);

        rcMenuList = findViewById<RecyclerView>(R.id.rcMenuList);

        menuAdapter = MenuAdapter(applicationContext);

        rcMenuList?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)

        rcMenuList?.adapter = menuAdapter


        //Network Call to Get All Categories

        apiService?.categories?.enqueue(object :  Callback<List<Category?>?> {
            override fun onResponse(
                call: Call<List<Category?>?>,
                response: Response<List<Category?>?>
            ) {

                val categories =  response.body();

                //Set Up Each Category on its own Tab
                for(category in categories!!){
                    tabLayout.addTab(tabLayout.newTab().setText(category?.name))
                }

                //load first category by default
                loadMenuByCategory(categories.get(0)?.id!!)

            }

            override fun onFailure(call: Call<List<Category?>?>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(applicationContext,"Could not get Categories! " , Toast.LENGTH_SHORT).show()            }

        })





        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                var tabPos = tab?.position!!;
                loadMenuByCategory(tabPos);

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
               //do nothing
            }


        })

        val slideModels: MutableList<SlideModel> = ArrayList()
        slideModels.add(
            SlideModel(
                "https://live.staticflickr.com/7006/6621416427_8504865e6a_z.jpg"
            )
        )
        slideModels.add(
            SlideModel(
                "https://cdn.pixabay.com/photo/2018/01/14/23/12/nature-3082832__340.jpg"
            )
        )
        slideModels.add(
            SlideModel(
                "https://live.staticflickr.com/7006/6621416427_8504865e6a_z.jpg"
            )
        )

        imageSlider.setImageList(slideModels, ScaleTypes.FIT) ;



    }


    //Load Menu per category & refresh adapter
    private fun loadMenuByCategory(category_id: Int) {
        apiService?.getCategoryMenu(category_id)?.enqueue( object: Callback<List<Menu?>?> {
            override fun onResponse(call: Call<List<Menu?>?>, response: Response<List<Menu?>?>) {
                //refresh adapter
                menuAdapter?.refresh(response.body() as List<Menu>)            }

            override fun onFailure(call: Call<List<Menu?>?>, t: Throwable) {
               t.printStackTrace()
            }
        })


    }
}

