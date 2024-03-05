package com.example.signaling

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.UUID


class ProfileFragment : Fragment() {
    var fileUriold: Bitmap? = null

    private  var fileUri: Uri? = null
    private  var name : String? = null

    private  var name2 : String? = null

    private  var pass : String? = null

    private  var email : String? = null
    private lateinit var mAuth: FirebaseAuth

    // private   var quan : String? = null
    private   var pic : String? = ""
    private   var picold : String? = ""

    var name1: EditText? = null
    private var email1: TextView? = null
    // private var nameorg: TextView? = null
    private var image : ImageView?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        val uid = user?.uid
        val parentNodeRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("buyers").child(uid.toString())
        image = view.findViewById(R.id.imageView)
        email1=view.findViewById(R.id.email1)
        //  name1= view.findViewById(R.id.editName)
        name1=view.findViewById(R.id.profname)

        var cancel = view.findViewById<Button>(R.id.cancell)
        cancel.setOnClickListener {

            val intent = Intent (getActivity(), Buyer::class.java)
            getActivity()?.startActivity(intent)}


        parentNodeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {




                    name= snapshot.child("name").getValue() as String?
                    //   nameorg!!.setText(name)
                //    pass=x.shopspass
                    email=snapshot.child("email").getValue() as String?
                  email1!!.setText(email)
                    name1!!.setText(name)
                pic=snapshot.child("pic").getValue() as String?
                if(pic.equals("")==false){
                        pic?.let { imgview(it,image!!) }


                }
                else{


                }






            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }


        )
        val imagddbutton: Button =view.findViewById(R.id.addimg)

        imagddbutton.setOnClickListener{
            pickimggallery()

        }
        val imagddbutton1: Button =view.findViewById(R.id.addprof)
        imagddbutton1.setOnClickListener {
            name=name1!!.text.toString()
            if (name!=null && !name.equals("") && !name.equals("name")){
            parentNodeRef.child("name").setValue(name)

            if(pic.equals("")==false){
                // add old pic
                parentNodeRef.child("pic").setValue(pic)

            }
else if(picold.equals("")==false){
                parentNodeRef.child("pic").setValue(picold)

                //add new pic
}

            val intent = Intent (getActivity(), Buyer::class.java)
            getActivity()?.startActivity(intent)}
         else{

                Toast.makeText(requireActivity().application, "please add a name ", Toast.LENGTH_LONG)
                    .show()

         }


        }

    }

    private fun imgview(x: String, image: ImageView) {
        val storageRef = FirebaseStorage.getInstance().reference
            .child(x)
        val localfile = File.createTempFile("tempImage", "jpg")
        //var bitmap = null
        storageRef.getFile(localfile).addOnSuccessListener {

            fileUriold = BitmapFactory.decodeFile(localfile.absolutePath)
            image!!.setImageBitmap(fileUriold)
            picold=x
            //holder.icons.setImageBitmap(fileUri)



        }.addOnFailureListener{

            Toast.makeText(requireActivity().application, "fail", Toast.LENGTH_LONG)
                .show()
        }
    }
    private fun pickimggallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent, AddFragment.IMAGE_REQUEST_CODE)


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddFragment.IMAGE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            if(data!=null && data.data!=null){
                fileUri=data.data
                uploadImage()
                image?.setImageURI(data?.data)

            }
        }
    }
    fun uploadImage() {
        if (fileUri != null) {
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Uploading Image...")
            progressDialog.setMessage("Processing...")
            progressDialog.show()
            val x = UUID.randomUUID().toString()
            pic=x

            val ref: StorageReference = FirebaseStorage.getInstance().getReference()
                .child(x)


            ref.putFile(fileUri!!).addOnSuccessListener {

                progressDialog.dismiss()

                Toast.makeText(requireActivity().application, "File Uploaded Successfully", Toast.LENGTH_LONG)
                    .show()

                //   imgview(x)

            }.addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(requireActivity().application, "File Upload Failed...", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }
}