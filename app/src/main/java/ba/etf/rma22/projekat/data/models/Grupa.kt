package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable

data class Grupa (
    val naziv: String,
    val nazivIstrazivanja: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun toString(): String {
        return naziv
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(naziv)
        parcel.writeString(nazivIstrazivanja)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Grupa> {
        override fun createFromParcel(parcel: Parcel): Grupa {
            return Grupa(parcel)
        }

        override fun newArray(size: Int): Array<Grupa?> {
            return arrayOfNulls(size)
        }
    }
}