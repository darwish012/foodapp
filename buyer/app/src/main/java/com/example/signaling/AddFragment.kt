package com.example.signaling

import android.app.ProgressDialog
import android.content.Intent
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.signaling.product.productsstr
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.UUID


class AddFragment : Fragment() {

    private  var fileUri: Uri? = null
    private  var name : String? = null
    private  var price : String? = null
    private   var quan : String? = null
    private   var pic : String? = null
    private var name1: EditText? = null
    private var price1: EditText? = null
    private var quan1: EditText? = null
    private var image : ImageView?=null

    private lateinit var mAuth: FirebaseAuth


    companion object {
        val IMAGE_REQUEST_CODE = 1_000;
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add,container,false)

        return view






        // Inflate the layout for this fragment

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        /*image = view.findViewById(R.id.addimage)

        name1=view.findViewById(R.id.prodname)


        price1=view.findViewById(R.id.prodprice)
         quan1=view.findViewById(R.id.prodquan)
        //val imagddbutton: Button=view.findViewById(R.id.updateprod)
        //imagddbutton.setOnClickListener{
          // pickimggallery()

        //}
        val prodaddbutton: Button=view.findViewById(R.id.editimg)
        prodaddbutton.setOnClickListener{
          //  name=null
            name =name1?.editableText.toString()
            price=price1?.editableText.toString()
            quan = quan1?.editableText.toString()



            if(name!=null&& price!=null&&quan!=null&&pic!=null){

               mAuth = FirebaseAuth.getInstance()

                val user = mAuth.currentUser
                val uid = user?.uid
                val parentNodeRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("sellers")

                val product1= productsstr(4,  name,price,quan)
                product1.pic= pic.toString()
                product1.productname?.let { it1 -> parentNodeRef.child(uid+"").child(it1).setValue(product1) }
                Toast.makeText(requireActivity().application, "toast added successfully", Toast.LENGTH_LONG)
                    .show()
            //    val intent = Intent(requireContext(), editprod::class.java)
                val intent = Intent (getActivity(), Seller::class.java)
                getActivity()?.startActivity(intent)
               // intent.putExtra("object1", productsstr)
                //   intent.putExtra("object2", fileUri)

           //     requireContext().startActivity(intent)
                //val fragment=ProductFragment()
               // val transaction = fragmentManager?.beginTransaction()
                //transaction?.replace(R.id.)
           }
            else{
                Toast.makeText(requireActivity().application, "please complete all required values", Toast.LENGTH_LONG)
                    .show()


            }


       }*/


    }


    private fun pickimggallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
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

    private fun imgview(x: String) {
        val storageRef =FirebaseStorage.getInstance().reference
            .child(x)
        val localfile = File.createTempFile("tempImage", "jpg")
        storageRef.getFile(localfile).addOnSuccessListener {

            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)


        //  binding.imageView.setImageBitmap(bitmap)

        }.addOnFailureListener{

            Toast.makeText(requireActivity().application, "Failed to retrive image..", Toast.LENGTH_LONG)
                .show()
        }

    }

}
