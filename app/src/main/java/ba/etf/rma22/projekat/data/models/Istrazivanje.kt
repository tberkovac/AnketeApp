package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Istrazivanje(
    @SerializedName("id") val id: Int,
    @SerializedName("naziv") val naziv: String,
    @SerializedName("godina") val godina: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return naziv
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
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