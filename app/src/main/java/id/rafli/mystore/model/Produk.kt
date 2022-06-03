package id.rafli.mystore.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Produk() : Serializable, Parcelable{
    var id:String = ""
    var code:String = ""
    var name:String = ""
    var price:String = ""
    var stok:String = ""
    var image:String = ""
    var deskripsi:String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readString() ?: ""
        code = parcel.readString() ?: ""
        name = parcel.readString() ?: ""
        price = parcel.readString() ?: ""
        stok = parcel.readString() ?: ""
        image = parcel.readString() ?: ""
        deskripsi = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(code)
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(stok)
        parcel.writeString(image)
        parcel.writeString(deskripsi)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Produk> {
        override fun createFromParcel(parcel: Parcel): Produk {
            return Produk(parcel)
        }

        override fun newArray(size: Int): Array<Produk?> {
            return arrayOfNulls(size)
        }
    }
}