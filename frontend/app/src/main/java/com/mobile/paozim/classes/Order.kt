package com.mobile.paozim.classes

import java.text.SimpleDateFormat
import java.util.Date

class Order(prodPrice: Float, shipPrice: Float, creator: User, store: Seller) {
    private val id: String = "0"
    private val seller: Seller = store
    private val client: User = creator
    private var itemsId = emptyArray<String>()
    private var amountPerItem = emptyArray<Int>()
    private var productPrice: Float = prodPrice
    private var shippingPrice: Float = shipPrice
    private var totalPrice: Float = productPrice + shippingPrice
    private val creationDate: String = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())


}
