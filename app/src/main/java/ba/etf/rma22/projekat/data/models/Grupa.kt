package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Grupa (
    @SerializedName("id") val id: Int,
    @SerializedName("naziv") val naziv: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
    ) {
    }

    override fun toString(): String {
        return naziv
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(naziv)
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