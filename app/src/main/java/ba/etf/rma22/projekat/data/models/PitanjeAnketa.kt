package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

data class PitanjeAnketa (
    @SerializedName("id") val id : Int,
    @SerializedName("student") val student: String,
    @SerializedName("progres") val progres: Double,
    @SerializedName("datumRada") val datumRada: Date?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readValue(Date::class.java.classLoader) as Date?
        ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(student)
        parcel.writeDouble(progres)
        parcel.writeDate(datumRada)
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

    fun Parcel.writeDate(date: Date?) {
        writeLong(date?.time ?: -1)
    }

    fun Parcel.readDate(): Date? {
        val long = readLong()
        return if (long != -1L) Date(long) else null
    }
}