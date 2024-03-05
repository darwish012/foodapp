package com.example.signaling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var butt = findViewById<Button>(R.id.btnCont)
        butt.setOnClickListener{
            startActivity(Intent(this, Buyer::class.java))
        }

    }
}