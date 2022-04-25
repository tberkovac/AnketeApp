package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Anketa(
    val naziv: String,
    val nazivIstrazivanja: String,
    val datumPocetak: Date,
    val datumKraj: Date,
    var datumRada: Date?,
    val trajanje: Int,
    val nazivGrupe: String,
    var progres: Float
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readValue(Date::class.java.classLoader) as Date,
        parcel.readValue(Date::class.java.classLoader) as Date,
        parcel.readValue(Date::class.java.classLoader) as? Date,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(naziv)
        parcel.writeString(nazivIstrazivanja)
        parcel.writeInt(trajanje)
        parcel.writeString(nazivGrupe)
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
    fun Parcel.writeDate(date: Date?) {
        writeLong(date?.time ?: -1)
    }

    fun Parcel.readDate(): Date? {
        val long = readLong()
        return if (long != -1L) Date(long) else null
    }

}
