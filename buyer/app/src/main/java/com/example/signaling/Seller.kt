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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Seller : AppCompatActivity() {

    private lateinit var pTabs: TabLayout
    private lateinit var ptitle: TextView
    private lateinit var pViewPager: ViewPager
    private lateinit var pagerAdapters: PagerAdapters
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        val uid = user?.uid
        val parentNodeRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("buyers").child(uid!!).child("cart")
        parentNodeRef.setValue(null)



      //  startActivity(Intent(this, Seller::class.java))
        val object1 = intent.getSerializableExtra("object1") as shops
        val bundle = Bundle()
        bundle.putSerializable("key", object1)

val fragment =ProductFragment()
        val fragment1=CartFragment()
        fragment1.arguments=bundle

        fragment.arguments=bundle
        pTabs = findViewById(R.id.tabs)

        pViewPager = findViewById(R.id.myPagerView)
        pagerAdapters = PagerAdapters(supportFragmentManager)

        pagerAdapters.addFragment(fragment,"Products")
        pagerAdapters.addFragment(fragment1,"Cart")


        pViewPager.adapter = pagerAdapters

        pTabs.setupWithViewPager(pViewPager)

    }

}