package com.example.signaling.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.signaling.Home
import com.example.signaling.R
import com.example.signaling.editprod
import com.example.signaling.product.productsstr
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.security.AccessController.getContext

class productAdapter (val context: Context,var arrayList: ArrayList<productsstr>):RecyclerView.Adapter<productAdapter.ItemHolder>(){
    var fileUri: Bitmap? = null
    //var fileUri: Uri? = null
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder=LayoutInflater.from(parent.context).inflate(R.layout.grid_layout_listitems,parent,false)
        return ItemHolder(itemHolder)

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var productsstr:productsstr=arrayList.get(position)

// holder.icons.setImageResource(productsstr.iconsproduct!!)
        val storageRef = FirebaseStorage.getInstance().reference
            .child(productsstr.pic)
        val localfile = File.createTempFile("tempImage", "jpg")
        //var bitmap = null
        storageRef.getFile(localfile).addOnSuccessListener {

            fileUri = BitmapFactory.decodeFile(localfile.absolutePath)
            holder.icons.setImageBitmap(fileUri)
            //        binding.imageView.setImageBitmap(bitmap)


        }.addOnFailureListener{

            //  Toast.makeText(applicationContext, "Failed to retrive image..", Toast.LENGTH_LONG).show()
        }

        holder.name.text ="name: "+productsstr.productname
        holder.price.text="price: "+productsstr.productprice
        holder.quantity.text="quantity: "+productsstr.productquan
        holder.icons.setOnClickListener{
           // Toast.makeText(context,productsstr.productname,Toast.LENGTH_LONG).show()
       //  var v = getContext()
            //context.startActivity(Intent(context, editprod::class.java))
            val intent = Intent(context, editprod::class.java)

            intent.putExtra("object1", productsstr)

            //intent.putExtra("object2", fileUri)

            //    intent.putExtra("object2", productsstr.productname)

            context.startActivity(intent)
        }
        holder.name.setOnClickListener{
            // Toast.makeText(context,productsstr.productname,Toast.LENGTH_LONG).show()
            //  var v = getContext()
          //  context.startActivity(Intent(context, editprod::class.java))
            val intent = Intent(context, editprod::class.java)

            intent.putExtra("object1", productsstr)
         //   intent.putExtra("object2", fileUri)

            context.startActivity(intent)
        }
        holder.quantity.setOnClickListener{
            // Toast.makeText(context,productsstr.productname,Toast.LENGTH_LONG).show()
            //  var v = getContext()
          //  context.startActivity(Intent(context, editprod::class.java))
            val intent = Intent(context, editprod::class.java)

            intent.putExtra("object1", productsstr)
          ////  intent.putExtra("object2", fileUri)

            context.startActivity(intent)
        }
        holder.price.setOnClickListener{
            // Toast.makeText(context,productsstr.productname,Toast.LENGTH_LONG).show()
            //  var v = getContext()
            //context.startActivity(Intent(context, editprod::class.java))
            val intent = Intent(context, editprod::class.java)

            intent.putExtra("object1", productsstr)
            //intent.putExtra("object2", fileUri)


            //intent.putExtra("object2", productsstr.productname)

            context.startActivity(intent)
        }


    }
    class ItemHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var icons =itemView.findViewById<ImageView>(R.id.icon_image)
        var name =itemView.findViewById<TextView>(R.id.product_name)
        var price =itemView.findViewById<TextView>(R.id.product_price)
        var quantity=itemView.findViewById<TextView>(R.id.product_quan)


    }
    private fun imgview(x: String) {

    }
}