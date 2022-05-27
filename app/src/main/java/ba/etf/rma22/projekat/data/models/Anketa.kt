package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

data class Anketa(

    @SerializedName("id") val id : Int,
    @SerializedName("naziv") val naziv: String,
    @SerializedName("datumPocetak") val datumPocetak: Date,
    @SerializedName("datumKraj") val datumKraj: Date,
    @SerializedName("datumKraj") val datumRada: Date?,

    @SerializedName("trajanje") val trajanje : Int,
    @SerializedName("nazivGrupe") val nazivGrupe : String,
    @SerializedName("nazivIstrazivanja") val nazivIstrazivanja: String,

    @SerializedName("progres") val progres : Float,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readValue(Date::class.java.classLoader) as Date,
        parcel.readValue(Date::class.java.classLoader) as Date,
        parcel.readValue(Date::class.java.classLoader) as? Date,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(naziv)
        parcel.writeInt(trajanje)
        parcel.writeString(nazivGrupe)
        parcel.writeString(nazivIstrazivanja)
        parcel.writeFloat(progres)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Anketa> {
        override fun createFromParcel(parcel: Parcel): Anketa {
            return Anketa(parcel)
        }

        override fun newArray(size: Int): Array<Anketa?> {
            return arrayOfNulls(size)
        }
    }
}