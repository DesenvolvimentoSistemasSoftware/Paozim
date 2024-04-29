package com.mobile.paozim.classes

import java.text.SimpleDateFormat
import java.util.Date

class User(userName: String, userEmail: String, userPhone: String?) {
    private val id: String = "0"
    private var name: String = userName
    private var email: String = userEmail
    private var photo: String? = null
    private var phone: String? = userPhone
    private val signupDate: String = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())
    private var address = emptyArray<String>()
    private val cart: Cart = Cart()
    private var paymentMethods = emptyArray<PaymentMethod>()
    private var history = emptyArray<Order>()
    private var ratings = emptyArray<Rating>()

    fun addPaymentMethod(cardNumber: Int) {
        val paymentMethod = PaymentMethod(cardNumber)
        paymentMethods += paymentMethod
    }

    fun addPhoto(img: String) {
        photo = img
    }

    fun addToCart(item: Product) {
        cart.addItem(item)
    }

    fun createOrder(order: Order) {
        history += order
    }

    fun rate(rating: Rating) {
        ratings += rating
    }
}
