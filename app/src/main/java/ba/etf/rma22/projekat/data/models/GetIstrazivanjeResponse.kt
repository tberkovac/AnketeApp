package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName

data class GetIstrazivanjeResponse (
    @SerializedName("istrazivanja") val istrazivanja : List<Istrazivanje>,
)