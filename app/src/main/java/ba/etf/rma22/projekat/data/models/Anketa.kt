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
    @SerializedName("trajanje") val trajanje : Int,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readValue(Date::class.java.classLoader) as Date,
        parcel.readValue(Date::class.java.classLoader) as Date,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(naziv)
        parcel.writeDate(datumPocetak)
        parcel.writeDate(datumKraj)
        parcel.writeInt(trajanje)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
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

    fun Parcel.writeDate(date: Date?) {
        writeLong(date?.time ?: -1)
    }

    fun Parcel.readDate(): Date? {
        val long = readLong()
        return if (long != -1L) Date(long) else null
    }
}