package com.mobile.paozim.classes

import android.os.Parcel
import android.os.Parcelable

data class Seller(
    val nome: String,
    val description: String,
    var image: String,
    val telefone: String,
    val CEP: String,
    val cidade: String,
    val estado: String,
    var bairro: String,
    val numResidencia: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeString(telefone)
        parcel.writeString(CEP)
        parcel.writeString(cidade)
        parcel.writeString(estado)
        parcel.writeString(bairro)
        parcel.writeInt(numResidencia)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Seller> {
        override fun createFromParcel(parcel: Parcel): Seller {
            return Seller(parcel)
        }

        override fun newArray(size: Int): Array<Seller?> {
            return arrayOfNulls(size)
        }
    }
}
