package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class Anketa(
    @PrimaryKey @SerializedName("id") val id : Int,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") val naziv: String,
    @NonNull @ColumnInfo(name = "datumPocetak") @SerializedName("datumPocetak") val datumPocetak: Date,
    @ColumnInfo(name = "datumKraj") @SerializedName("datumKraj") val datumKraj: Date?,
    @ColumnInfo(name = "trajanje") @SerializedName("trajanje") val trajanje : Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readValue(Date::class.java.classLoader) as Date,
        parcel.readValue(Date::class.java.classLoader) as Date,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(naziv)
        parcel.writeDate(datumPocetak)
        parcel.writeDate(datumKraj)
        parcel.writeInt(trajanje)
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Anketa

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}