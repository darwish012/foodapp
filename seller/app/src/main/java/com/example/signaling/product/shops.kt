package com.example.signaling.product

import java.io.Serializable

class shops: Serializable {
    var iconsshops:Int ?=0
   // var productprice:String ?=null
   // var productquan:String ?=null
    var shopsname:String =""
    var shopsmail:String =""
    var shopspass:String =""
    var uid:String=""

    var pic : String=""
    constructor(iconsproduct:Int?, productname: String ,productmail: String ,productpass: String ){
      //  this.productquan=productquan
        this.iconsshops=iconsproduct
        this.shopsname=productname
        this.shopsmail=productmail
        this.shopspass=productpass

     //   this.productname=productname
    }
    constructor(){

    }
}