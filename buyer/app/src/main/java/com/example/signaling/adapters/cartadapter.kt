package com.example.signaling.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.signaling.R
import com.example.signaling.product.productsstr
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class cartadapter (var context: Context, var arrayList: ArrayList<productsstr>): RecyclerView.Adapter<cartadapter.ItemHolder>(){
    private lateinit var mAuth: FirebaseAuth
    var fileUri: Bitmap? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemHolder=
            LayoutInflater.from(parent.context).inflate(R.layout.grid_layout_cartitems,parent,false)
        return ItemHolder(itemHolder)

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        var productsstr1: productsstr =arrayList.get(position)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        val uid = user?.uid
        val parentNodeRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("buyers").child(uid!!).child("cart")
        val storageRef = FirebaseStorage.getInstance().reference.child(productsstr1.pic)
        val localfile = File.createTempFile("tempImage", "jpg")
        var bitmap = null
        storageRef.getFile(localfile).addOnSuccessListener {

            fileUri = BitmapFactory.decodeFile(localfile.absolutePath)
            holder.icons.setImageBitmap(fileUri)
            //        binding.imageView.setImageBitmap(bitmap)


        }.addOnFailureListener{

            //  Toast.makeText(applicationContext, "Failed to retrive image..", Toast.LENGTH_LONG).show()
        }
        holder.name.text =productsstr1.productname
        holder.price.text=productsstr1.productprice


        holder.quantity.text=productsstr1.userquan
        var pq: String= holder.quantity.text as String
        var pp:String=holder.price.text as String
        var tp:Int=pp.toInt()*pq.toInt()
        holder.total_price.text="Total:"+tp.toString()

        holder.plus.setOnClickListener {
            var g: String= holder.quantity.text as String
            if(g.toInt()<productsstr1.productquan!!.toInt()){
                holder.quantity.text= (g.toIntOrNull()?.plus(1))!!.toString()
                var pq: String= holder.quantity.text as String
                var pp:String=holder.price.text as String
                var tp:Int=pp.toInt()*pq.toInt()
                holder.total_price.text="Total:"+tp.toString()
                var productsstr2:productsstr =productsstr(1,productsstr1.productname,productsstr1.productprice,productsstr1.productquan)
                productsstr2.userquan= holder.quantity.text as String                //parentNodeRef.child(productsstr2.productname!!).setValue(null)
                productsstr2.pic=productsstr1.pic

                parentNodeRef.child(productsstr2.productname!!).setValue(productsstr2)


            }
            else{
                Toast.makeText(context, "exceeded quantity", Toast.LENGTH_LONG).show()

            }


        }
        holder.minus.setOnClickListener {
            var g: String= holder.quantity.text as String
            if(g.toInt()>0){
                holder.quantity.text= (g.toIntOrNull()?.minus(1))!!.toString()
                var pq: String= holder.quantity.text as String
                var pp:String=holder.price.text as String
                var tp:Int=pp.toInt()*pq.toInt()
                holder.total_price.text= "Total:" +tp.toString()
                if(holder.quantity.text.toString().toInt()==0){
                    var productsstr2:productsstr =productsstr(1,productsstr1.productname,productsstr1.productprice,productsstr1.productquan)
                    productsstr2.userquan= holder.quantity.text as String                    // parentNodeRef.child(productsstr2.productname!!).setValue(null)
                    productsstr2.pic=productsstr1.pic

                    parentNodeRef.child(productsstr2.productname!!).setValue(productsstr2)

                    parentNodeRef.child(productsstr1.productname!!).setValue(null)

                }
                else{
                    var productsstr2:productsstr =productsstr(1,productsstr1.productname,productsstr1.productprice,productsstr1.productquan)
                    productsstr2.userquan= holder.quantity.text as String                    // parentNodeRef.child(productsstr2.productname!!).setValue(null)
                    productsstr2.pic=productsstr1.pic

                    parentNodeRef.child(productsstr2.productname!!).setValue(productsstr2)

                }

            }
        }
    }
    class ItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var icons =itemView.findViewById<ImageView>(R.id.icon_image)
        var name =itemView.findViewById<TextView>(R.id.product_name)
        var price =itemView.findViewById<TextView>(R.id.product_price)
        var quantity=itemView.findViewById<TextView>(R.id.product_quan)
        var plus =itemView.findViewById<ImageView>(R.id.cartaddbutton)
        var minus =itemView.findViewById<ImageView>(R.id.cartminusbutton)
        var total_price =itemView.findViewById<TextView>(R.id.total_product_price)



    }
    private fun imgview(x: String) {

    }
}