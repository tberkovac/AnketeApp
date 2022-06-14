package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ba.etf.rma22.projekat.data.AnketaIGrupeDao2
import com.google.gson.annotations.SerializedName

@Entity
data class AnketaiGrupe2 (
    @ColumnInfo(name = "GrupaId")
    @SerializedName("GrupaId") val GrupaId: Int,
    @ColumnInfo(name = "AnketumId")
    @SerializedName("AnketumId") val AnketumId: Int,
) : Parcelable {
    @PrimaryKey(autoGenerate = true) var id : Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(GrupaId)
        parcel.writeInt(AnketumId)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AnketaiGrupe2

        if (GrupaId != other.GrupaId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = GrupaId
        result = 31 * result + AnketumId
        return result
    }

    companion object CREATOR : Parcelable.Creator<AnketaiGrupe2> {
        override fun createFromParcel(parcel: Parcel): AnketaiGrupe2 {
            return AnketaiGrupe2(parcel)
        }

        override fun newArray(size: Int): Array<AnketaiGrupe2?> {
            return arrayOfNulls(size)
        }
    }
}
