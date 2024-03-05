package com.example.signaling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class Buyer : AppCompatActivity() {
    private lateinit var pTabs: TabLayout
    private lateinit var ptitle: TextView
    private lateinit var pViewPager: ViewPager
    private lateinit var pagerAdapters: PagerAdapters
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer)
        pTabs = findViewById(R.id.tabsB)

        pViewPager = findViewById(R.id.myPagerViewB)
        pagerAdapters = PagerAdapters(supportFragmentManager)

        pagerAdapters.addFragment(ShopFragment(),"Shops")
        pagerAdapters.addFragment(ProfileFragment(),"Profile")


        pViewPager.adapter = pagerAdapters

        pTabs.setupWithViewPager(pViewPager)
    }
}