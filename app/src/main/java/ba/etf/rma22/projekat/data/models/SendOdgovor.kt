package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SendOdgovor (
    @SerializedName("odgovor") val odgovor: Int,
    @SerializedName("pitanje") val pitanje: Int,
    @SerializedName("progres") val progres : Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(odgovor)
        parcel.writeInt(pitanje)
        parcel.writeInt(progres)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SendOdgovor> {
        override fun createFromParcel(parcel: Parcel): SendOdgovor {
            return SendOdgovor(parcel)
        }

        override fun newArray(size: Int): Array<SendOdgovor?> {
            return arrayOfNulls(size)
        }
    }
}