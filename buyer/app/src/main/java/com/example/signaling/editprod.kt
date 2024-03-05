package com.example.signaling

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.signaling.databinding.ActivityEditprodBinding
import com.example.signaling.databinding.ActivitySignUpBinding
import com.example.signaling.product.productsstr
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.UUID

class editprod : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding : ActivityEditprodBinding
    var fileUriold: Bitmap? = null
    var fileUrinew: Uri? = null
    var pic=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprod)
        binding = ActivityEditprodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

       val object1 = intent.getSerializableExtra("object1") as productsstr

  //     val object2 = intent.getSerializableExtra("object2") as Bitmap?
        imgview(object1.pic)
//binding.addimage.setImageBitmap(object2)
            binding.prodname.setText(object1.productname)
       binding.prodprice.setText(object1.productprice)
        binding.prodquan.setText(object1.productquan)

binding.cancel.setOnClickListener{
    startActivity(Intent(this, Seller::class.java))


}
        binding.editimg.setOnClickListener{

            pickimggallery()
        }
binding.updateprod.setOnClickListener{
    val user = mAuth.currentUser
    val uid = user?.uid
    val parentNodeRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("sellers").child(uid.toString())
    var x = productsstr(1, binding.prodname.text.toString(), binding.prodprice.text.toString(), binding.prodquan.text.toString())
    if(fileUriold==null){
        x.pic=pic
    }
    else{
        x.pic=object1.pic

    }
    parentNodeRef.child(x.productname.toString()).setValue(x)
    startActivity(Intent(this, Seller::class.java))

    if(object1.productname.toString().equals(binding.prodname.text.toString()) == false){
        parentNodeRef.child(object1.productname.toString()).setValue(null)


    }

}
    }

    private fun imgview(x: String) {
        val storageRef = FirebaseStorage.getInstance().reference
            .child(x)
        val localfile = File.createTempFile("tempImage", "jpg")
        //var bitmap = null
        storageRef.getFile(localfile).addOnSuccessListener {

            fileUriold = BitmapFactory.decodeFile(localfile.absolutePath)

            //holder.icons.setImageBitmap(fileUri)
                   binding.addimage.setImageBitmap(fileUriold)


        }.addOnFailureListener{

            //  Toast.makeText(applicationContext, "Failed to retrive image..", Toast.LENGTH_LONG).show()
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
                fileUrinew=data.data
                fileUriold=null
                binding.addimage.setImageURI(fileUrinew)
                uploadImage()

                //  image?.setImageURI(data?.data)

            }
        }
    }
    fun uploadImage() {
        if (fileUrinew != null) {
            //val progressDialog = ProgressDialog(context)
            //progressDialog.setTitle("Uploading Image...")
            //progressDialog.setMessage("Processing...")
            //progressDialog.show()
            val x = UUID.randomUUID().toString()
           pic=x

            val ref: StorageReference = FirebaseStorage.getInstance().getReference()
                .child(x)


            ref.putFile(fileUrinew!!).addOnSuccessListener {

              //  progressDialog.dismiss()

       //         Toast.makeText(requireActivity().application, "File Uploaded Successfully", Toast.LENGTH_LONG).show()

                //   imgview(x)

            }.addOnFailureListener {
             //   progressDialog.dismiss()
               // Toast.makeText(requireActivity().application, "File Upload Failed...", Toast.LENGTH_LONG) .show()
            }
        }

    }


}