package ba.etf.rma22.projekat.data.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class GetAnketaResponse (
    @SerializedName("ankete") val ankete : List<Anketa>,
)


