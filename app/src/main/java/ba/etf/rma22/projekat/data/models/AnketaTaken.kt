package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

data class AnketaTaken (
    @SerializedName("id") val id : Int,
    @SerializedName("student") val student: String,
    @SerializedName("progres") var progres : Int,
    @SerializedName("datumRada") val datumRada : Date,
    @SerializedName("AnketumId") var anketumId : Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readValue(Date::class.java.classLoader) as Date,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(student)
        parcel.writeInt(progres)
        parcel.writeDate(datumRada)
        parcel.writeInt(anketumId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnketaTaken> {
        override fun createFromParcel(parcel: Parcel): AnketaTaken {
            return AnketaTaken(parcel)
        }

        override fun newArray(size: Int): Array<AnketaTaken?> {
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