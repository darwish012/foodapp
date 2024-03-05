package com.example.signaling.product

import java.io.Serializable

class orders: Serializable{
    var products:ArrayList<productsstr> ?=null
    var uid:String ?=null
    var status:Int = 0  // -1 declined , 0 waiting , 1 accepted and being prepared , 2 delivred
var add :String=""
    var price:Double=0.0


    constructor(products:ArrayList<productsstr>?, uid: String ?){
        this.products=products
        this.uid=uid

    }
    constructor(){

    }

}
