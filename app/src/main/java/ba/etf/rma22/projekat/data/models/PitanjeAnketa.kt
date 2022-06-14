package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PitanjeAnketa (
    @SerializedName("AnketumId") val AnketumId: Int,
    @SerializedName("PitanjeId") val PitanjeId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(AnketumId)
        parcel.writeInt(PitanjeId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PitanjeAnketa> {
        override fun createFromParcel(parcel: Parcel): PitanjeAnketa {
            return PitanjeAnketa(parcel)
        }

        override fun newArray(size: Int): Array<PitanjeAnketa?> {
            return arrayOfNulls(size)
        }
    }
}