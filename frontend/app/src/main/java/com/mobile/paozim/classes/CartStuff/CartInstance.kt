package com.mobile.paozim.classes.CartStuff

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.mobile.paozim.classes.Item

object CartInstance {
    var Carro = Cart(mutableListOf(), null, null)

    fun addItem(context: Context, storeId: String, item: Item, qtd: Int, delivery: Double) {
        if (Carro.storeID != null) {
            if (Carro.storeID == storeId) {
                val index = Carro.itens.indexOfFirst { it.id == item.id }
                if (index != -1) {
                    Carro.itens[index].qtd += qtd
                } else {
                    Carro.itens.add(CartItem(item.id, item.name, item.image, item.price, qtd))
                }
                Carro.shippingPrice = delivery
                saveCart(context)
            } else {
                Log.d("CART2", "Carrinho de outra loja")
            }
        } else {
            Carro.storeID = storeId
            Carro.itens = mutableListOf(CartItem(item.id, item.name, item.image, item.price, qtd))
            Carro.shippingPrice = delivery
            saveCart(context)
        }
    }
    fun removeItem(context: Context, item: CartItem) {
        val index = Carro.itens.indexOfFirst { it.id == item.id }
        Carro.itens.removeAt(index)
        if(Carro.itens.isEmpty()){
            Carro.storeID = null
            Carro.shippingPrice = null
        }
        saveCart(context)
    }

    fun decreaseItem(context: Context, item: CartItem) {
        val index = Carro.itens.indexOfFirst { it.id == item.id }
        if (Carro.itens[index].qtd > 1) {
            Carro.itens[index].qtd--
        } else {
            Carro.itens.removeAt(index)
        }
        saveCart(context)
    }
    fun increaseItem(context: Context, item: CartItem) {
        val index = Carro.itens.indexOfFirst { it.id == item.id }
        Carro.itens[index].qtd++
        saveCart(context)
    }

    fun getSubtotal(): Double {
        var subtotal = 0.0
        Carro.itens.forEach {
            subtotal += it.price * it.qtd
        }
        return subtotal
    }

    private fun saveCart(context: Context) {
        val sharedPreferences = context.getSharedPreferences("CarrinhoPrefs", Context.MODE_PRIVATE)
        val jsonString = GsonBuilder().create().toJson(Carro)
        sharedPreferences.edit().putString("CartF", jsonString).apply()
        Log.d("CART", jsonString)
    }
    fun loadCart(context: Context) {
        val sharedPreferences = context.getSharedPreferences("CarrinhoPrefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("CartF", null)
        if (!json.isNullOrEmpty()) {
            Log.d("CART", json)
            Carro = GsonBuilder().create().fromJson(json, Cart::class.java)
        } else {
            Log.d("CART", "null")
        }
    }
}
