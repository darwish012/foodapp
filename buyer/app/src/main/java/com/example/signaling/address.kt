package com.example.signaling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.signaling.product.orders
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class address : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        var add = findViewById<EditText>(R.id.address)
        var cont = findViewById<Button>(R.id.cont)
        var o = intent.getSerializableExtra("object1") as orders
        var shop = intent.getSerializableExtra("o2") as shops
var pric =findViewById<TextView>(R.id.total)
        pric.setText("total :  "+o.price)
        var noadd = findViewById<Button>(R.id.noadd)
        noadd.setOnClickListener {
            val parentNodeRef1: DatabaseReference = FirebaseDatabase.getInstance().reference
            o.add= "recieve at shop"

            parentNodeRef1.child("sellers orders").child(shop!!.uid).child(o.uid!!).setValue(o)

            val intent = Intent(this, order::class.java)
            intent.putExtra("object1", o)

            intent.putExtra("o2",shop)
            startActivity(intent)
        }


        cont.setOnClickListener {

            var t =add.text
            if(t==null ||t.equals("") || t.equals("address yaba")|| t.isEmpty()){

                Toast.makeText(this, "yabaaa", Toast.LENGTH_LONG).show()

            }
            else{

                val parentNodeRef1: DatabaseReference = FirebaseDatabase.getInstance().reference
                o.add= t.toString()

                parentNodeRef1.child("sellers orders").child(shop!!.uid).child(o.uid!!).setValue(o)

                val intent = Intent(this, order::class.java)
                intent.putExtra("object1", o)

                intent.putExtra("o2",shop)
                startActivity(intent)

            }

        }

    }
}