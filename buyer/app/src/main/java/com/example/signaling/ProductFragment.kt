package com.example.signaling

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.signaling.adapters.productAdapter
import com.example.signaling.product.productsstr
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductFragment : Fragment() {
    private var recyclerView: RecyclerView?=null
    private var gridLayoutManager: GridLayoutManager?=null
    private var arrayList:ArrayList<productsstr>?=null
    private var ProductAdapter: productAdapter?=null
     private lateinit var mAuth: FirebaseAuth
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
            var list:ArrayList<Bundle>?=ArrayList()
            //list=arguments?.getSerializable("key") as? shops

            shopsData = arguments?.getSerializable("key") as? shops

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        val uid = user?.uid

      //  val object1 = requireActivity().application.getSerializableExtra("object1") as shops


        recyclerView=view.findViewById(R.id.my_rv1)
        gridLayoutManager= GridLayoutManager(requireActivity().application,2, LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager=gridLayoutManager
        recyclerView?.setHasFixedSize(true)
        val parentNodeRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("sellers").child(shopsData!!.uid)
        val childrenList = arrayListOf<productsstr>()
      //  childrenList.add(productsstr(R.drawable.sinacola,"sinacola","2 gneeeeh",shopsData!!.uid))
        //  childrenList.add(productsstr(R.drawable.sinacola,"error","2 gneeeeh",uid))
        // childrenList.add(productsstr(R.drawable.sinacola,childcount.toString(),"2 gneeeeh",uid))
        ProductAdapter=productAdapter(requireContext(), childrenList)
        recyclerView?.adapter=ProductAdapter
        parentNodeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Get the number of children
                var i =0
                for (item in snapshot.children){
                    var x =  item.getValue(productsstr::class.java)
                    item.getValue(productsstr::class.java)?.let { childrenList.add(it) }
                    childrenList.get(i).pic= item.child("pic").getValue(String::class.java).toString()
                    i=i+1
                    if (x != null) {
                        Log.d("product class", x.productname.toString())
                    }
                }
                ProductAdapter!!.notifyDataSetChanged()
            //    childcount = snapshot.childrenCount.toInt()

                //  Log.d("YourActivity", "Number of Children: $numberOfChildren")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors if there are any
            }
        })


        val parentNodeRef1: DatabaseReference = FirebaseDatabase.getInstance().reference.child("buyers").child(uid!!).child("cart")
        parentNodeRef1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Get the number of children
                var i =0
                //  childrenList.clear()
                for (item in snapshot.children){
                    var x =  item.getValue(productsstr::class.java)
                    //item.getValue(productsstr::class.java)?.let { childrenList.add(it) }
                    // childrenList.get(i).pic= item.child("pic").getValue(String::class.java).toString()
                   for(productsstr1 in childrenList){

                    if(x!!.productname.equals(productsstr1.productname)){
                      productsstr1.userquan= x.userquan.toString()

                    }}
                }
               ProductAdapter!!.notifyDataSetChanged()
                //    childcount = snapshot.childrenCount.toInt()

                //  Log.d("YourActivity", "Number of Children: $numberOfChildren")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors if there are any
            }
        })

   /*
        val parentNodeRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("sellers").child(uid.toString())
      //  var parentNodeRef1: DatabaseReference = FirebaseDatabase.getInstance().reference.child("sellers")

        var childcount :Int =0
        val childrenList = arrayListOf<productsstr>()


        parentNodeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Get the number of children
                var i =0
                for (item in snapshot.children){
                    var x =  item.getValue(productsstr::class.java)
                    item.getValue(productsstr::class.java)?.let { childrenList.add(it) }
                    childrenList.get(i).pic= item.child("pic").getValue(String::class.java).toString()
                    i=i+1
                    if (x != null) {
                        Log.d("product class", x.productname.toString())
                    }
                }

                childcount = snapshot.childrenCount.toInt()
            //    childrenList.add(productsstr(R.drawable.sinacola,"sinacola","2 gneeeeh",uid))
              //  childrenList.add(productsstr(R.drawable.sinacola,"error","2 gneeeeh",uid))
               // childrenList.add(productsstr(R.drawable.sinacola,childcount.toString(),"2 gneeeeh",uid))
            ProductAdapter=productAdapter(requireContext(), setDataInList())
                recyclerView?.adapter=ProductAdapter
                //  Log.d("YourActivity", "Number of Children: $numberOfChildren")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors if there are any
            }
        })


    //    val x = parentNodeRef.get()


*/
       // ProductAdapter=productAdapter(requireContext(), setDataInList())
        //recyclerView?.adapter=ProductAdapter


    }
    private fun setDataInList():ArrayList<productsstr>{
        var items:ArrayList<productsstr> =ArrayList()

        items.add(productsstr(R.drawable.sinacola,"sinacola","2 gneeeeh","100"))
        items.add(productsstr(R.drawable.sinacola,"sinapepsi","4 gneeeeh","100"))
        items.add(productsstr(R.drawable.sinacola,"sinapepsi","4 gneeeeh","100"))
        items.add(productsstr(R.drawable.sinacola,"sinapepsi","4 gneeeeh","100"))
        items.add(productsstr(R.drawable.sinacola,"sinapepsi","4 gneeeeh","100"))
        items.add(productsstr(R.drawable.sinacola,"sinapepsi","4 gneeeeh","100"))
        items.add(productsstr(R.drawable.sinacola,"sinapepsi","4 gneeeeh","100"))
        items.add(productsstr(R.drawable.sinacola,"sinapepsi","4 gneeeeh","100"))
        return items

    }
}



