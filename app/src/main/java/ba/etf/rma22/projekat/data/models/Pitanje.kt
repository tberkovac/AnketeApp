package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Pitanje (
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") val naziv: String,
    @ColumnInfo(name = "tekstPitanja") @SerializedName("tekstPitanja") val tekstPitanja: String,
    @ColumnInfo(name = "opcije") @SerializedName("opcije") val opcije: List<String>,
    @ColumnInfo(name = "PitanjeAnketa") @SerializedName("PitanjeAnketa") val PitanjeAnketa: PitanjeAnketa,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readParcelable<PitanjeAnketa>(ClassLoader.getSystemClassLoader())!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(naziv)
        parcel.writeString(tekstPitanja)
        parcel.writeStringList(opcije)
        parcel.writeParcelable(PitanjeAnketa,1)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pitanje> {
        override fun createFromParcel(parcel: Parcel): Pitanje {
            return Pitanje(parcel)
        }

        override fun newArray(size: Int): Array<Pitanje?> {
            return arrayOfNulls(size)
        }
    }
}