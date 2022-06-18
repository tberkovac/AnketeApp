package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Odgovor (
    @ColumnInfo(name = "odgovoreno") @SerializedName("odgovoreno") val odgovoreno :Int,
    @ColumnInfo(name = "AnketaTakenId") @SerializedName ("AnketaTakenId") val anketaTakenId : Int,
    @ColumnInfo(name = "PitanjeId") @SerializedName("PitanjeId") val pitanjeId : Int
) : Parcelable {

    @PrimaryKey(autoGenerate = true) var id : Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(odgovoreno)
        parcel.writeInt(anketaTakenId)
        parcel.writeInt(pitanjeId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Odgovor> {
        override fun createFromParcel(parcel: Parcel): Odgovor {
            return Odgovor(parcel)
        }

        override fun newArray(size: Int): Array<Odgovor?> {
            return arrayOfNulls(size)
        }
    }
}