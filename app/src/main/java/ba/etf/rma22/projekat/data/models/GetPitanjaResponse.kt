package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class GetPitanjaResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("naziv") val naziv: String,
    @SerializedName("tekstPitanja") val tekstPitanja: String,
    @SerializedName("opcije") val opcije: List<Pitanje>
)