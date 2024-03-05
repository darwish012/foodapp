package com.example.signaling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.signaling.adapters.productAdapter
import com.example.signaling.product.productsstr
import com.google.android.material.tabs.TabLayout

class Seller : AppCompatActivity() {

    private lateinit var pTabs: TabLayout
    private lateinit var ptitle: TextView
    private lateinit var pViewPager: ViewPager
    private lateinit var pagerAdapters: PagerAdapters


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller)



      //  startActivity(Intent(this, Seller::class.java))


        pTabs = findViewById(R.id.tabs)

        pViewPager = findViewById(R.id.myPagerView)
        pagerAdapters = PagerAdapters(supportFragmentManager)

        pagerAdapters.addFragment(ProductFragment(),"Products")
        pagerAdapters.addFragment(AddFragment(),"Add Product")
        pagerAdapters.addFragment(OrderFragment(),"Orders")
        pagerAdapters.addFragment(ProfileFragment(),"Profile")


        pViewPager.adapter = pagerAdapters

        pTabs.setupWithViewPager(pViewPager)

    }

}