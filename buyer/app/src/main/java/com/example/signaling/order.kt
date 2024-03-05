package com.example.signaling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.signaling.product.orders
import com.example.signaling.product.productsstr
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class order : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        var x =findViewById<TextView>(R.id.textView99)
        var o = intent.getSerializableExtra("object1") as orders
var shop = intent.getSerializableExtra("o2") as shops


        val parentNodeRef1: DatabaseReference = FirebaseDatabase.getInstance().reference
        var text = ""


        parentNodeRef1.child("sellers orders").child(shop.uid.toString()).child(o.uid.toString()).child("status").addValueEventListener(object :


            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Get the number of children
            var c= snapshot.getValue(Int::class.java)
            if(c==-1) {
                x.setText("order cancelled")

            }

            else if (c==1){
                x.setText("order accepted and being prepared")
            }
            else if (c==2){
                x.setText("done enjoy :)")
            }


            }
            override fun onCancelled(error: DatabaseError) {
                // Handle errors if there are any
            }
        })



    }
}