package com.mobile.paozim.classes

class Cart {
    private var nItems: Int = 0
    private var items = emptyArray<Product>()

    fun addItem(item: Product) {
        items += item
        nItems++
    }
}
