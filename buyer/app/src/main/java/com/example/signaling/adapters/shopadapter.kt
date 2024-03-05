package com.example.signaling.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.signaling.R
import com.example.signaling.Seller
import com.example.signaling.editprod


import com.example.signaling.shops
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class shopadapter (val context: Context, var arrayList: ArrayList<shops>): RecyclerView.Adapter<shopadapter.ItemHolder>(){
    var fileUri: Bitmap? = null
    //var fileUri: Uri? = null
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder=
            LayoutInflater.from(parent.context).inflate(R.layout.grid_layout_shops,parent,false)
        return ItemHolder(itemHolder)

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var shop: shops =arrayList.get(position)
        if(shop.pic.equals("")==false) {
            val storageRef = FirebaseStorage.getInstance().reference
                .child(shop.pic)
// holder.icons.setImageResource(productsstr.iconsproduct!!)
            val localfile = File.createTempFile("tempImage", "jpg")
            //var bitmap = null
            storageRef.getFile(localfile).addOnSuccessListener {

                fileUri = BitmapFactory.decodeFile(localfile.absolutePath)
                holder.icons.setImageBitmap(fileUri)
                //        binding.imageView.setImageBitmap(bitmap)


            }.addOnFailureListener {

                //  Toast.makeText(applicationContext, "Failed to retrive image..", Toast.LENGTH_LONG).show()
            }
            //var bitmap = null
        }

        holder.name?.text =shop.shopsname
holder.icons.setOnClickListener {

    val intent = Intent(context, Seller::class.java)
    intent.putExtra("object1", shop)
    context.startActivity(intent)

}
        holder.name.setOnClickListener {

           val intent = Intent(context, Seller::class.java)
            intent.putExtra("object1", shop)
            context.startActivity(intent)

        }





    }
    class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //change r.id to shop r.id
        var icons =itemView.findViewById<ImageView>(R.id.icon_image)
        var name =itemView.findViewById<TextView>(R.id.shop_name)



    }
    private fun imgview(x: String) {

    }
}