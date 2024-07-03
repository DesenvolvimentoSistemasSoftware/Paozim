package com.mobile.paozim.classes.cartStuff

data class Cart(
    var itens: MutableList<CartItem>,
    var storeID: String?,
    var shippingPrice: Double?
)
