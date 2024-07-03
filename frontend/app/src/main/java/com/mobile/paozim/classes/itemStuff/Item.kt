package com.mobile.paozim.classes.itemStuff

import android.os.Parcel
import android.os.Parcelable

data class Item(
    val id: Int,
    val name: String,
    val sellerID: Int,
    val price: Double,
    val stock: Int,
    var image: String,
    val description: String,
    val avgRate: Double
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble()
    ) {}

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(sellerID)
        parcel.writeDouble(price)
        parcel.writeInt(stock)
        parcel.writeString(image)
        parcel.writeString(description)
        parcel.writeDouble(avgRate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}
