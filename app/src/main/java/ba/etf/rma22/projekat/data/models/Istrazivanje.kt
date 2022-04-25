package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable

data class Istrazivanje(
    val naziv: String,
    val godina: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return naziv
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(naziv)
        parcel.writeInt(godina)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Istrazivanje> {
        override fun createFromParcel(parcel: Parcel): Istrazivanje {
            return Istrazivanje(parcel)
        }

        override fun newArray(size: Int): Array<Istrazivanje?> {
            return arrayOfNulls(size)
        }
    }
}