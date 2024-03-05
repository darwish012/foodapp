package com.example.signaling

import java.io.Serializable

class shops: Serializable {
    var iconsshops:Int ?=0
    var shopsname:String ?=""
    var shopsmail:String =""
    var shopspass:String =""
    var uid:String=""

    var pic : String=""
    constructor(shopicon:Int?, shopname: String ?){
        this.iconsshops=shopicon
        this.shopsname=shopname
    }

    constructor(){

    }
}