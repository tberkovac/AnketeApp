package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class AnketaTaken (
    @PrimaryKey @SerializedName("id") val id : Int,
    @ColumnInfo(name = "student") @SerializedName("student") val student: String,
    @ColumnInfo(name = "progres") @SerializedName("progres") var progres : Int,
    @ColumnInfo(name = "datumRada") @SerializedName("datumRada") val datumRada : Date,
    @ColumnInfo(name = "AnketumId") @SerializedName("AnketumId") var AnketumId : Int
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
        parcel.writeInt(AnketumId)
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