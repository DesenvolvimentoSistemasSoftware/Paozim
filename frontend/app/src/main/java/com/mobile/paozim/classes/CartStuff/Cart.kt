package com.mobile.paozim.classes.CartStuff

data class Cart(
    var itens: MutableList<CartItem>,
    var storeID: String?,
    var shippingPrice: Double?
)
