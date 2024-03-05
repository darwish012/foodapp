package com.example.signaling

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.signaling.adapters.productAdapter
import com.example.signaling.adapters.shopadapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ShopFragment : Fragment() {
    private var recyclerView: RecyclerView?=null
    private var gridLayoutManager: GridLayoutManager?=null
    private var arrayList:ArrayList<shops>?=null
    private var shopadapter: shopadapter?=null
    private lateinit var mAuth: FirebaseAuth
    var name=""

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
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        val uid = user?.uid*/
        recyclerView=view.findViewById(R.id.my_rv3)
        gridLayoutManager= GridLayoutManager(requireActivity().application,2, LinearLayoutManager.VERTICAL,false)
        recyclerView?.layoutManager=gridLayoutManager
        recyclerView?.setHasFixedSize(true)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        val uid = user?.uid
        val parentNodeRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("sellers info")

        val childrenList = arrayListOf<shops>()
        parentNodeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (item in snapshot.children){
                    var x =  item.getValue(shops::class.java)
                    //  name= item.child("shopname").getValue(String::class.java).toString()
                    if (x != null) {
                        childrenList.add(x)
                    }
                    //   childrenList.get(i).pic= item.child("pic").getValue(String::class.java).toString()


                }
                // arrayList=ArrayList()
                //arrayList=setDataInList()
                //     arrayList!!.add(shops(R.drawable.sinacola,"sinacola"))

                shopadapter=shopadapter(requireContext(),childrenList!!)
                recyclerView?.adapter=shopadapter


            }
            override fun onCancelled(error: DatabaseError) {
                // Handle errors if there are any
            }
        })

















        //val parentNodeRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("sellers").child(uid.toString())
        //var childcount :Int =0
        //val childrenList = arrayListOf<productsstr>()


        /* parentNodeRef.addListenerForSingleValueEvent(object : ValueEventListener {
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
                 ProductAdapter=productAdapter(requireContext(), childrenList)
                 recyclerView?.adapter=ProductAdapter
                 //  Log.d("YourActivity", "Number of Children: $numberOfChildren")
             }

             override fun onCancelled(error: DatabaseError) {
                 // Handle errors if there are any
             }
         })


         //    val x = parentNodeRef.get()




 */


    }
    private fun setDataInList():ArrayList<shops>{
        var items:ArrayList<shops> =ArrayList()

        items.add(shops(R.drawable.sinacola,"sinacola"))
        items.add(shops(R.drawable.sinacola,"sinacola"))
        items.add(shops(R.drawable.sinacola,"sinacola"))

        return items

    }


}