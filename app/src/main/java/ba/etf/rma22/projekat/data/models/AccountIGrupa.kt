package ba.etf.rma22.projekat.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class AccountIGrupa (
    @ColumnInfo(name = "AccountId")
    @SerializedName("AccountId") val AccountId: Int,
    @ColumnInfo(name = "GrupaId")
    @SerializedName("GrupaId") val GrupaId: Int,
) : Parcelable {

    @PrimaryKey(autoGenerate = true) var id : Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(AccountId)
        parcel.writeInt(GrupaId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccountIGrupa> {
        override fun createFromParcel(parcel: Parcel): AccountIGrupa {
            return AccountIGrupa(parcel)
        }

        override fun newArray(size: Int): Array<AccountIGrupa?> {
            return arrayOfNulls(size)
        }
    }
}