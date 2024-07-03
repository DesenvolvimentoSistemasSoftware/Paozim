package com.mobile.paozim.classes.cartStuff

data class CartItem(
    val id:Int,
    val name:String,
    val image:String,
    val price:Double,
    var qtd:Int
)
