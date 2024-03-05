package com.example.signaling

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.signaling.adapters.cartadapter
import com.example.signaling.adapters.productAdapter
import com.example.signaling.product.orders
import com.example.signaling.product.productsstr
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.Serializable


class CartFragment : Fragment(),Serializable {
    public var cart:ArrayList<productsstr>?=null
    private var recyclerView: RecyclerView?=null
    private var gridLayoutManager: GridLayoutManager?=null
    private var cartAdapter: cartadapter?=null
    private lateinit var mAuth: FirebaseAuth
    var price=0.0

    val childrenList = arrayListOf<productsstr>()
    var shopsData:shops?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shopsData = arguments?.getSerializable("key") as? shops

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        val uid = user?.uid
        val parentNodeRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("buyers").child(uid!!).child("cart")
        recyclerView = view.findViewById(R.id.my_rv3)
        gridLayoutManager = GridLayoutManager(requireActivity().application, 1, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)
        var price_beforetaxes=view.findViewById<TextView>(R.id.total_price)
        price_beforetaxes.text=get_totalprices(childrenList).toString()
        var priceaftertax=view.findViewById<TextView>(R.id.aftertax)
        priceaftertax.text= (price_beforetaxes.text.toString().toInt()*1.05).toString()
        var orderbutt=view.findViewById<Button>(R.id.ordernow)


       var cancel=view.findViewById<Button>(R.id.cancelcart)

cancel.setOnClickListener {
parentNodeRef.setValue(null)
    val intent = Intent(context, Seller::class.java)
    intent.putExtra("object1", shopsData)
    context?.startActivity(intent)
}
        orderbutt.setOnClickListener{
            if (childrenList.isEmpty()==false){
price= (get_totalprices(childrenList)*1.05)
              //  val parentNodeRef1: DatabaseReference = FirebaseDatabase.getInstance().reference
               var o = orders(childrenList,uid)
                o.price=price
               // parentNodeRef1.child("sellers orders").child(shopsData!!.uid).child(uid).setValue(o)

                val intent = Intent(context, address::class.java)
                intent.putExtra("object1", o)
                intent.putExtra("o2",shopsData)
                context?.startActivity(intent)

        }
            else{
                Toast.makeText(requireContext(), "ordered nothing ya 3abeet", Toast.LENGTH_LONG).show()
            }


        }

        cart= ArrayList()

        cart=setDataInList()
        cartAdapter= cartadapter(requireContext(), childrenList!!)
        recyclerView?.adapter=cartAdapter

        parentNodeRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Get the number of children
                var i =0
                childrenList.clear()
                for (item in snapshot.children){
                    var x =  item.getValue(productsstr::class.java)
                    item.getValue(productsstr::class.java)?.let { childrenList.add(it) }
                //    childrenList.get(i).pic= item.child("pic").getValue(String::class.java).toString()
                    i=i+1
                    if (x != null) {
                        Log.d("product class", x.productname.toString())
                    }
                }
                cartAdapter!!.notifyDataSetChanged()
                price_beforetaxes.text=get_totalprices(childrenList).toString()
                var priceaftertax=view.findViewById<TextView>(R.id.aftertax)
                priceaftertax.text= (price_beforetaxes.text.toString().toInt()*1.05).toString()


                //    childcount = snapshot.childrenCount.toInt()

                //  Log.d("YourActivity", "Number of Children: $numberOfChildren")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors if there are any
            }
        })





        //hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh

    }
    private fun setDataInList():ArrayList<productsstr>{
        var items:ArrayList<productsstr> =ArrayList()

        items.add(productsstr(R.drawable.sinacola,"sinacola","2","100"))
        items.add(productsstr(R.drawable.sinacola,"sinapepsi","4","100"))
        items.add(productsstr(R.drawable.sinacola,"sinapepsi","4","100"))
        items.add(productsstr(R.drawable.sinacola,"sinapepsi","5","100"))
        items.add(productsstr(R.drawable.sinacola,"sinapepsi","4","100"))
        items.add(productsstr(R.drawable.sinacola,"zengi","400","100"))
        items.add(productsstr(R.drawable.sinacola,"zengi","400","100"))
        items.add(productsstr(R.drawable.sinacola,"yahooody","400","100"))
        return items

    }

    private fun isalredyin(childrenList: ArrayList<productsstr>, product: productsstr?): Boolean{

        if(childrenList.isEmpty()){
            return false
        }
        else{

            for(item in childrenList){
                if(item.productname.equals(product!!.productname)){
                    return true
                }

            }
            return false
        }

    }
    private fun get_totalprices(childrenList: ArrayList<productsstr>): Int{
        var x:Int=0
        for (product in childrenList) {
            x = x + product.productprice!!.toInt() * product.userquan!!.toInt()
        }
        return x
    }
}