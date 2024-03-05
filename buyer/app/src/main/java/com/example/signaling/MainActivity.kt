package com.example.signaling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var mail: EditText? = null
    private var pass: EditText? = null
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        mAuth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var butt = findViewById<Button>(R.id.loginButt)
        var text2 = findViewById<TextView>(R.id.text1)
        var text1 = findViewById<TextView>(R.id.text2)
        mail = findViewById<EditText>(R.id.editText1)
        pass = findViewById<EditText>(R.id.editText2)


        butt.setOnClickListener {
            val email: String = mail?.editableText.toString()
            val password: String = pass?.editableText.toString()

            if (TextUtils.isEmpty(email)) {
                mail?.error = "Please enter your email"
            } else if (TextUtils.isEmpty(password)) {
                pass?.error = "Please enter your password"
            }
            else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "User Login Successful", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                    startActivity(Intent(this, Home::class.java))
                } else {
                    Toast.makeText(
                        this,
                        "User Login Failed due to ${it.exception}",
                        Toast.LENGTH_LONG).show()
                }
            }
            }
        }
        text1.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }

    private fun loginUser() {

    }
}

