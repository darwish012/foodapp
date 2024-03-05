package com.example.signaling.product

import android.text.Editable
import java.io.Serializable

class productsstr: Serializable {
    var iconsproduct:Int ?=0
    var productprice:String ?=null
    var productquan:String ?=null
    var productname:String ?=null
    var pic : String=""
    var userquan:String="0"
    constructor(iconsproduct:Int?, productname: String ?, productprice:String ?, productquan:String?){
        this.productquan=productquan
        this.iconsproduct=iconsproduct
        this.productprice=productprice
        this.productname=productname
    }
    constructor(){

    }

}