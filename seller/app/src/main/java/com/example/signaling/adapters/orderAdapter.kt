package com.example.signaling.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.signaling.R
import com.example.signaling.product.orders
import com.example.signaling.product.productsstr
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class orderAdapter (val context: Context, var arrayList: ArrayList<orders> ): RecyclerView.Adapter<orderAdapter.ItemHolder>() {
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): orderAdapter.ItemHolder {
        val itemHolder=
            LayoutInflater.from(parent.context).inflate(R.layout.grid_layout_orders,parent,false)
        return ItemHolder(itemHolder)
    }

    override fun getItemCount(): Int {

            return arrayList.size
    }

    override fun onBindViewHolder(holder: orderAdapter.ItemHolder, position: Int) {
        var o:orders=arrayList.get(position)

        mAuth = FirebaseAuth.getInstance()


        val user = mAuth.currentUser
        val uid = user?.uid

        val parentNodeRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("buyers").child(o.uid!!).child("name")
parentNodeRef.addListenerForSingleValueEvent(object :
    ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        var t=""
        t=  "order from :  "+snapshot.getValue(String::class.java).toString()+"\n"+"address:  "+o.add+"\n" +"order details:"+"\n"
        for (prod in o.products!!){

            t+=prod.productname +"   quan : "+prod.userquan+ "\n"
        }
        t+="total price : "+o.price
        holder.orderdetails.setText(t)
        val parentNodeRef1: DatabaseReference = FirebaseDatabase.getInstance().reference

        parentNodeRef1.child("sellers").child(uid.toString()).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    var product = item.getValue(productsstr::class.java)

                        for(prod in o.products!!){
                            if(product!!.productname.equals(prod!!.productname)){
                                prod.productquan=product.productquan } } }




            }
            override fun onCancelled(error: DatabaseError) {
                // Handle errors if there are any
            }  }  )





        holder.denybutton.setOnClickListener {
            val parentNodeRef11: DatabaseReference = FirebaseDatabase.getInstance().reference
o.status=-1
            parentNodeRef11.child("sellers orders").child(uid.toString()).child(o.uid.toString()).setValue(o)

            parentNodeRef11.child("sellers orders").child(uid.toString()).child(o.uid.toString()).setValue(null)



        }

        holder.acceptbutton.setOnClickListener {

            if(holder.acceptbutton.text.equals("click here if the order is received by the buyer")){
                val parentNodeRef11: DatabaseReference = FirebaseDatabase.getInstance().reference
                o.status=2
                parentNodeRef11.child("sellers orders").child(uid.toString()).child(o.uid.toString()).setValue(o)
                parentNodeRef11.child("sellers orders").child(uid.toString()).child(o.uid.toString()).setValue(null)

                holder.denybutton.visibility=View.INVISIBLE

            }
           else{
                val parentNodeRef11: DatabaseReference = FirebaseDatabase.getInstance().reference
                var n =true
for(prod in o.products!!){
    if((prod.productquan!!.toInt()-prod.userquan!!.toInt())<0){
        n=false
        o.status=-1
        parentNodeRef11.child("sellers orders").child(uid.toString()).child(o.uid.toString()).setValue(o)

        parentNodeRef11.child("sellers orders").child(uid.toString()).child(o.uid.toString()).setValue(null)

        break
    }

}
       if(n){
                for(prod1 in o.products!!){
               var prod=prod1.copy1()
                    prod.productquan= (prod.productquan!!.toInt()-prod.userquan!!.toInt()).toString()
                    //افتكر تهندل لو كذه حد بيطلبوا فى نفس الوقت
                   prod.userquan="0"
                    parentNodeRef11.child("sellers").child(uid!!).child(prod.productname!!).setValue(prod)

               }

            o.status=1
            parentNodeRef11.child("sellers orders").child(uid.toString()).child(o.uid.toString()).setValue(o)
holder.denybutton.visibility=View.INVISIBLE

holder.acceptbutton.setText("click here if the order is received by the buyer")


           }
        else{


           Toast.makeText(context, "can not accept this order because a previous order that you accepted reduced the quantity available ", Toast.LENGTH_LONG).show()

        }



           }

        }



    }



    override fun onCancelled(error: DatabaseError) {
        // Handle errors if there are any
    }
})

    }
    class ItemHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var orderdetails =itemView.findViewById<TextView>(R.id.textView2)
       var acceptbutton=itemView.findViewById<Button>(R.id.but2)
       var denybutton=itemView.findViewById<Button>(R.id.but1)



    }
}