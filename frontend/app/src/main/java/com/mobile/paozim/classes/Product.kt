package com.mobile.paozim.classes

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val nome: String,
    val vendedorID: String,
    val preco: Double,
    var estoque: Int, //sem uso agora
    var desconto: Double,
    val imagens: Array<String>, //apenas 1 imagem
    val descricao: String,
    val categorias: Array<String>,
    var aveAvaliacao: Double,
    val avaliacaoID: IntArray //sem uso agora
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.createStringArray()!!,
        parcel.readString()!!,
        parcel.createStringArray()!!,
        parcel.readDouble(),
        parcel.createIntArray()!!
    ) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nome)
        parcel.writeString(vendedorID)
        parcel.writeDouble(preco)
        parcel.writeInt(estoque)
        parcel.writeDouble(desconto)
        parcel.writeStringArray(imagens)
        parcel.writeString(descricao)
        parcel.writeStringArray(categorias)
        parcel.writeDouble(aveAvaliacao)
        parcel.writeIntArray(avaliacaoID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
