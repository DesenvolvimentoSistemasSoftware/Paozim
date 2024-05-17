package com.mobile.paozim.classes.CartStuff

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.mobile.paozim.classes.Product

object CartInstance {
    var Carro = Cart(mutableListOf(), null, null)

    fun addItem(context: Context, storeId: String, item: Product, qtd: Int, delivery: Double) {
        if (Carro.storeID != null) {
            if (Carro.storeID == storeId) {
                val index = Carro.itens.indexOfFirst { it.id == item.id }
                if (index != -1) {
                    Carro.itens[index].qtd += qtd
                } else {
                    Carro.itens.add(CartItem(item.id, item.nome, item.imagens[0], item.preco, qtd))
                }
                Carro.shippingPrice = delivery
                safeCart(context)
            } else {
                Log.d("CART", "Carrinho de outra loja")
            }
        } else {
            Carro.storeID = storeId
            Carro.itens = mutableListOf(CartItem(item.id, item.nome, item.imagens[0], item.preco, qtd))
            Carro.shippingPrice = delivery
            safeCart(context)
        }
    }
    fun removeItem(context: Context, item: Product) {
        val index = Carro.itens.indexOfFirst { it.id == item.id }
        Carro.itens.removeAt(index)
        if(Carro.itens.isEmpty()){
            Carro.storeID = null
            Carro.shippingPrice = null
        }
        safeCart(context)
    }

    fun decreaseItem(context: Context, item: Product) {
        val index = Carro.itens.indexOfFirst { it.id == item.id }
        if (Carro.itens[index].qtd > 1) {
            Carro.itens[index].qtd--
        } else {
            Carro.itens.removeAt(index)
        }
        safeCart(context)
    }
    fun increaseItem(context: Context, item: Product) {
        val index = Carro.itens.indexOfFirst { it.id == item.id }
        Carro.itens[index].qtd++
        safeCart(context)
    }

    fun getSubtotal(): Double {
        var subtotal = 0.0
        Carro.itens.forEach {
            subtotal += it.price * it.qtd
        }
        return subtotal
    }

    private fun safeCart(context: Context) {
        val sharedPreferences = context.getSharedPreferences("Carrinho", Context.MODE_PRIVATE)
        val jsonString = GsonBuilder().create().toJson(Carro)
        sharedPreferences.edit().putString("CartF", jsonString).apply()
        Log.d("CART", jsonString)
    }
    fun loadCart(context: Context) {
        val sharedPreferences = context.getSharedPreferences("Carrinho", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("CartF", null)
        if (!json.isNullOrEmpty()) {
            Log.d("CART", json)
            Carro = GsonBuilder().create().fromJson(json, Cart::class.java)
        } else {
            Log.d("CART", "null")
        }
    }
}
