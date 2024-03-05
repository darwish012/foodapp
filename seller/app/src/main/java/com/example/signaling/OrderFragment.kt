package com.example.signaling

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.signaling.adapters.orderAdapter
import com.example.signaling.adapters.productAdapter
import com.example.signaling.product.orders
import com.example.signaling.product.productsstr
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



class OrderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var recyclerView: RecyclerView?=null
    private var gridLayoutManager: GridLayoutManager?=null
    private var arrayList:ArrayList<productsstr>?=null
    private var orderAdapter: orderAdapter?=null
    private lateinit var mAuth: FirebaseAuth
    var ll =""
    var name =""

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
        return inflater.inflate(R.layout.fragment_order, container, false)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView=view.findViewById(R.id.my_rv0)
        gridLayoutManager= GridLayoutManager(requireActivity().application,1, LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager=gridLayoutManager
        recyclerView?.setHasFixedSize(true)
        var childrenList1 = arrayListOf<orders>()
    //    var c = orders(setDataInList(),"dgdf")
     //   childrenList1.add(c)
        orderAdapter=orderAdapter(requireContext(), childrenList1)
        recyclerView?.adapter=orderAdapter

        mAuth = FirebaseAuth.getInstance()


        val user = mAuth.currentUser
        val uid = user?.uid

        val parentNodeRef1: DatabaseReference = FirebaseDatabase.getInstance().reference
        var text = ""

        parentNodeRef1.child("sellers orders").child(uid.toString()).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                childrenList1.clear()
                for (item in snapshot.children) {
                    var order = item.getValue(orders::class.java)
                    if (order != null) {
                        childrenList1.add(order)   }}
               orderAdapter!!.notifyDataSetChanged()  }
            override fun onCancelled(error: DatabaseError) {
                // Handle errors if there are any
            }  }  )

parentNodeRef1.child("sellers").child(uid.toString()).addValueEventListener(object :
    ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        for (item in snapshot.children) {
            var product = item.getValue(productsstr::class.java)
            for(order in childrenList1){
                for(prod in order.products!!){
                    if(product!!.productname.equals(prod!!.productname)){
                        prod.productquan=product.productquan } } } }


    orderAdapter!!.notifyDataSetChanged()

    }
    override fun onCancelled(error: DatabaseError) {
        // Handle errors if there are any
    }  }  )



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