package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UpisGrupeResponse (
    @SerializedName("message") val message :String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UpisGrupeResponse> {
        override fun createFromParcel(parcel: Parcel): UpisGrupeResponse {
            return UpisGrupeResponse(parcel)
        }

        override fun newArray(size: Int): Array<UpisGrupeResponse?> {
            return arrayOfNulls(size)
        }
    }
}