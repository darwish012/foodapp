package com.example.signaling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.signaling.R.*
import com.example.signaling.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        val uid = user?.uid

        binding.buttonsignin.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))

        }

        binding.regester.setOnClickListener {

            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()
            if (email.isEmpty()) {
                binding.name.error = "name required"
                binding.name.requestFocus()

            } else if (email.isEmpty()) {
                binding.email.error = "email required"
                binding.email.requestFocus()

            } else if (pass.isEmpty()) {
                binding.password.error = "password required"
                binding.password.requestFocus()

            } else {
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "register succesful", Toast.LENGTH_SHORT).show()

                        val user = mAuth.currentUser
                        val uid = user?.uid
                        val parentNodeRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("buyers")

                    //    parentNodeRef.child(uid+"").setValue(name)
                    //    val product= Product("ddd",  5,7,"aa")
                      //  parentNodeRef.child(uid+"").child(product.name).setValue(product)
                        parentNodeRef.child(uid+"").child("name").setValue(name)

                        parentNodeRef.child(uid+"").child("email").setValue(email)
                        parentNodeRef.child(uid+"").child("pic").setValue("")


                        finish()
                        startActivity(Intent(this, MainActivity::class.java))

                    } else {
                        Toast.makeText(this, "register unsuccesful", Toast.LENGTH_LONG).show()

                    }
                }

            }

        }
    }

}
