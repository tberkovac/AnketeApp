package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Odgovor2 (
    @PrimaryKey(autoGenerate = true)  val id : Int,
    @ColumnInfo(name ="odgovoreno") @SerializedName("odgovoreno") val odgovoreno : Int,
//    @ColumnInfo(name ="AnketaTakenId") @SerializedName("AnketaTakenId") val AnketaTakenId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
   //     parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(odgovoreno)
   //     parcel.writeInt(AnketaTakenId)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Odgovor2

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<Odgovor2> {
        override fun createFromParcel(parcel: Parcel): Odgovor2 {
            return Odgovor2(parcel)
        }

        override fun newArray(size: Int): Array<Odgovor2?> {
            return arrayOfNulls(size)
        }
    }
}