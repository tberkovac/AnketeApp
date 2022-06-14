package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Grupa (
    @PrimaryKey @SerializedName("id") val id: Int,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") val naziv: String,
    @ColumnInfo(name = "IstrazivanjeId") @SerializedName("IstrazivanjeId") val IstrazivanjeId: Int,
    @ColumnInfo(name = "AnketaiGrupe") @SerializedName("AnketaiGrupe") val AnketaiGrupe2 : AnketaiGrupe2?,
    @ColumnInfo(name = "UpisaneGrupe") @SerializedName("UpisaneGrupe") val UpisaneGrupe : AccountIGrupa?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readParcelable<AnketaiGrupe2>(ClassLoader.getSystemClassLoader()),
        parcel.readParcelable<AccountIGrupa>(ClassLoader.getSystemClassLoader())
    ) {
    }

    override fun toString(): String {
        return naziv
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(naziv)
        parcel.writeInt(IstrazivanjeId)
        parcel.writeParcelable(AnketaiGrupe2, 1)
        parcel.writeParcelable(UpisaneGrupe, 1)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Grupa

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<Grupa> {
        override fun createFromParcel(parcel: Parcel): Grupa {
            return Grupa(parcel)
        }

        override fun newArray(size: Int): Array<Grupa?> {
            return arrayOfNulls(size)
        }
    }
}