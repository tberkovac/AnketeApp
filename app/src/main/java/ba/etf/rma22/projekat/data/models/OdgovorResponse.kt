package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class OdgovorResponse (
    @SerializedName("odgovoreno") val odgovoreno :Int,
    @SerializedName ("AnketaTakenId") val anketaTakenId : Int,
    @SerializedName("PitanjeId") val pitanjeId : Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(odgovoreno)
        parcel.writeInt(anketaTakenId)
        parcel.writeInt(pitanjeId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OdgovorResponse> {
        override fun createFromParcel(parcel: Parcel): OdgovorResponse {
            return OdgovorResponse(parcel)
        }

        override fun newArray(size: Int): Array<OdgovorResponse?> {
            return arrayOfNulls(size)
        }
    }
}